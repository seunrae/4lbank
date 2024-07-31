package com.example.seun4L._LBank.user.service.impl;

import com.example.seun4L._LBank.bank.model.Bank;
import com.example.seun4L._LBank.bank.repository.BankRepository;
import com.example.seun4L._LBank.bank.service.BankService;
import com.example.seun4L._LBank.exception.BankNotFoundException;
import com.example.seun4L._LBank.exception.TransactionNotFoundException;
import com.example.seun4L._LBank.exception.UserNotFoundException;
import com.example.seun4L._LBank.transaction.dto.TransactionResponseDto;
import com.example.seun4L._LBank.transaction.model.Transaction;
import com.example.seun4L._LBank.transaction.repository.TransactionRepository;
import com.example.seun4L._LBank.transaction.service.TransactionService;
import com.example.seun4L._LBank.user.dto.*;
import com.example.seun4L._LBank.user.model.ACCOUNTSTATUS;
import com.example.seun4L._LBank.user.model.ACCOUNTTYPE;
import com.example.seun4L._LBank.user.model.ROLE;
import com.example.seun4L._LBank.user.model.User;
import com.example.seun4L._LBank.user.repository.UserRepository;
import com.example.seun4L._LBank.user.service.JwtService;
import com.example.seun4L._LBank.user.service.UserService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BankService bankService;
    private final BankRepository bankRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final int USERID = (int) Math.ceil(Math.random() * 10_000);

    @Override
    public ResponseEntity<?> createUser(UserRegisterDto userRegisterDto) {
        try{
            if (userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
                String accountNumber = userRegisterDto.getAccountType().toUpperCase().equals(String.valueOf(ACCOUNTTYPE.SAVINGS)) ?
                        generateSavingsAccountNumber() : generateCurrentAccountNumber();
                User user = new User();
                Bank bank = bankRepository.findByBankCode("01").orElseThrow(()-> new BankNotFoundException("Bank not found"));
                user.setUserId(String.valueOf(USERID));
                user.setFirstName(userRegisterDto.getFirstName());
                user.setLastName(userRegisterDto.getLastName());
                user.setMiddleName(userRegisterDto.getMiddleName());
                user.setEmail(userRegisterDto.getEmail());
                user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
                user.setPin(passwordEncoder.encode(userRegisterDto.getPin()));
                user.setPhoneNumber(userRegisterDto.getPhoneNumber());
                user.setAddress(userRegisterDto.getAddress());
                user.setStateOfOrigin(userRegisterDto.getStateOfOrigin());
                user.setCountry(userRegisterDto.getCountry());
                user.setAccountName(user.getLastName() + " " + user.getFirstName() + ' ' + user.getMiddleName());
                user.setAccountNumber(accountNumber);
                user.setAccountStatus(ACCOUNTSTATUS.ACTIVE);
                user.setAccountType(ACCOUNTTYPE.valueOf(userRegisterDto.getAccountType().toUpperCase()));
                user.setAccountBalance(BigDecimal.valueOf(0.00));
                user.setRole(ROLE.CUSTOMER);
                user.setBank(bank);
                userRepository.save(user);

                LOGGER.info("Response :: \n" + getUser(user));
                return new ResponseEntity<>(getUser(user), HttpStatus.CREATED);
            }
            return new ResponseEntity<>("password does not match ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> userLogin(UserLoginDto userLoginDto) throws UserNotFoundException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
            User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
            var jwt = jwtService.generateToken(user);

            JwtResponseDto jwtResponseDto = new JwtResponseDto();
            jwtResponseDto.setEmail(user.getEmail());
            jwtResponseDto.setAccountNumber(user.getAccountNumber());
            jwtResponseDto.setAccountType(user.getAccountType().toString());
            jwtResponseDto.setToken(jwt);
            return new ResponseEntity<>(jwtResponseDto, HttpStatus.OK);
        } catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(UserRequestDto userRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setLastName(userRequestDto.getLastName());
            user.setEmail(userRequestDto.getEmail());
            user.setPhoneNumber(userRequestDto.getPhoneNumber());
            user.setPin(passwordEncoder.encode(userRequestDto.getPin()));
            user.setAddress(userRequestDto.getAddress());
            user.setStateOfOrigin(userRequestDto.getStateOfOrigin());
            user.setAccountName(userRequestDto.getLastName() + " " + userRequestDto.getFirstName() + ' ' + userRequestDto.getMiddleName());
            user.setCountry(userRequestDto.getCountry());
            userRepository.save(user);
            return new ResponseEntity<>(getUser(user), HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepository.delete(user);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getUserById() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponseEntity<>(getUser(user), HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getUserByAccountNumber(String accountNumber) {
        try {
            User user = userRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new UserNotFoundException("User with account number " + accountNumber + " not found"));
            return new ResponseEntity<>(getUser(user), HttpStatus.OK);
        }catch(Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(getUsers(users), HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> debitUserAccount(UserTransactionDto userTransactionDto) {
        try {
            User user = userRepository.findByAccountNumber(userTransactionDto.getAccountNumber()).orElseThrow(() -> new UserNotFoundException("User with account number " + userTransactionDto.getAccountNumber() + " not found"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(userTransactionDto.getPin(), user.getPin())) {
                if (user.getAccountBalance().compareTo(userTransactionDto.getAmount()) >= 0) {
                    user.setAccountBalance(user.getAccountBalance().subtract(userTransactionDto.getAmount()));
                    //user.getTransactions().add(transaction);
                    userRepository.save(user);
                    TransactionResponseDto transactionResponseDto = transactionService.createTransaction(user, userTransactionDto.getAmount(), "debit", userTransactionDto.getUserDescription());
                    return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
                }
                return new ResponseEntity<>("Insufficient funds", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Incorrect pin", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> creditUserAccount(UserTransactionDto userTransactionDto) {
        try {
            User user = userRepository.findByAccountNumber(userTransactionDto.getAccountNumber()).orElseThrow(() ->
                    new UserNotFoundException("User with account number " + userTransactionDto.getAccountNumber() + " not found"));
            user.setAccountBalance(user.getAccountBalance().add(userTransactionDto.getAmount()));
            userRepository.save(user);
            TransactionResponseDto transactionResponseDto = transactionService.createTransaction(user, userTransactionDto.getAmount(), "credit", userTransactionDto.getUserDescription());
            return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> transferFunds(UserTransferDto userTransferDto) {
        //debit the customer(with bank charges)
        //credit the bank
        //debit the bank
        //credit the destination customer
        try {
            User user = userRepository.findByAccountNumber(userTransferDto.getAccountNumber()).orElseThrow(() -> new UserNotFoundException("User with account number " + userTransferDto.getAccountNumber() + " not found"));
            User receiver = userRepository.findByAccountNumber(userTransferDto.getDestinationAccountNumber()).orElseThrow(()-> new UserNotFoundException("User with account number " + userTransferDto.getAccountNumber() + " not found"));

            Bank bank = bankRepository.findByBankCode("01").orElseThrow(()-> new BankNotFoundException("Bank not found"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(userTransferDto.getPin(), user.getPin())) {
                if (user.getAccountBalance().compareTo(userTransferDto.getAmount()) >= 0) {
                    float transferCharge = 10.25f;
                    BigDecimal totalAmount = userTransferDto.getAmount().add(BigDecimal.valueOf(transferCharge));
                    user.setAccountBalance(user.getAccountBalance().subtract(totalAmount));
                    userRepository.save(user);
                    TransactionResponseDto transactionResponseDto = transactionService.createTransferTransaction(user, receiver.getAccountName(), userTransferDto.getAmount(), "Transfer", userTransferDto.getUserDescription());

                    bank.setAccountBalance(bank.getAccountBalance().add(totalAmount));
                    bank.setAccountBalance(bank.getAccountBalance().subtract(userTransferDto.getAmount()));
                    bankRepository.save(bank);

                    receiver.setAccountBalance(receiver.getAccountBalance().add(userTransferDto.getAmount()));
                    userRepository.save(receiver);
                    transactionService.createTransaction(receiver, userTransferDto.getAmount(), "credit", userTransferDto.getUserDescription());
                    return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
                }
                return new ResponseEntity<>("Insufficient funds", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Incorrect pin", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> printStatement(String accountNumber, String email) {
        try {
            User user = userRepository.findByEmailAndAccountNumber(email, accountNumber)
                    .orElseThrow(() -> new UserNotFoundException("User email or account number doesnt exist"));

            List<TransactionResponseDto> transactionResponseDtos = transactionService.getTransactionResponses(user.getTransactions());
            return new ResponseEntity<>(transactionResponseDtos, HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAccountBalance(String accountNumber) {
        try {
            User user = userRepository.findByAccountNumber(accountNumber).orElseThrow(()-> new UserNotFoundException("User with account number " + accountNumber + " not found"));
            Map<String, String> response = new HashMap<>();
            response.put("balance", user.getAccountBalance().toString());
            response.put("name", user.getLastName() + " " + user.getFirstName() + ' ' + user.getMiddleName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateSavingsAccountNumber(){
        int remainingDigits = (int) Math.ceil(Math.random() * 1_000_000);
        Year initialDigits = Year.now();
        String accountNumber = initialDigits + String.valueOf(remainingDigits);

        return accountNumber;
    }

    private String generateCurrentAccountNumber() {
        int remainingDigits = (int) Math.ceil(Math.random() * 1_000_000);
        Year initialDigits = Year.now().minusYears(1024);
        String accountNumber = initialDigits + String.valueOf(remainingDigits);
        return accountNumber;
    }

    private UserTransactionResponse getUserTransaction(User user, BigDecimal amount, String transactionType) {
        UserTransactionResponse userTransactionResponse = new UserTransactionResponse();
        userTransactionResponse.setAccountName(user.getAccountName());
        userTransactionResponse.setAccountNumber(user.getAccountNumber());
        userTransactionResponse.setAccountBalance(user.getAccountBalance());
        userTransactionResponse.setAmount(String.valueOf(amount));
        userTransactionResponse.setStatus("Success");
        userTransactionResponse.setTransactionType(transactionType);
        return userTransactionResponse;
    }


    private UserResponseDto getUser(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setMiddleName(user.getMiddleName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setAddress(user.getAddress());
        userResponseDto.setStateOfOrigin(user.getStateOfOrigin());
        userResponseDto.setCountry(user.getCountry());
        userResponseDto.setAccountStatus(user.getAccountStatus());
        userResponseDto.setAccountType(user.getAccountType());
        userResponseDto.setAccountName(user.getAccountName());
        userResponseDto.setAccountNumber(user.getAccountNumber());
        userResponseDto.setAccountBalance(user.getAccountBalance());
        userResponseDto.setRole(user.getRole());
        return userResponseDto;
    }

    private List<UserResponseDto> getUsers(List<User> users){
        List<UserResponseDto> userResponseDtos = new LinkedList<>();

        for (var user: users) {
            UserResponseDto userResponseDto = new UserResponseDto();

            userResponseDto.setId(user.getId());
            userResponseDto.setUserId(user.getUserId());
            userResponseDto.setFirstName(user.getFirstName());
            userResponseDto.setLastName(user.getFirstName());
            userResponseDto.setMiddleName(user.getFirstName());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setPhoneNumber(user.getPhoneNumber());
            userResponseDto.setAddress(user.getAddress());
            userResponseDto.setStateOfOrigin(user.getStateOfOrigin());
            userResponseDto.setCountry(user.getCountry());
            userResponseDto.setAccountStatus(user.getAccountStatus());
            userResponseDto.setAccountType(user.getAccountType());
            userResponseDto.setAccountName(user.getAccountName());
            userResponseDto.setAccountNumber(user.getAccountNumber());
            userResponseDto.setAccountBalance(user.getAccountBalance());
            userResponseDto.setRole(user.getRole());

            userResponseDtos.add(userResponseDto);
        }

        return userResponseDtos;
    }
}
