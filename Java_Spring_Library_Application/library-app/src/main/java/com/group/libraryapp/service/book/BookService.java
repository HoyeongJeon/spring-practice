package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequestDTO;
import com.group.libraryapp.dto.book.request.BookLoanRequestDTO;
import com.group.libraryapp.dto.book.request.BookReturnRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequestDTO request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequestDTO request) {
        // 1. 책 정보를 가져온다.
        Book book = bookRepository.findByName(request.getBookName()).orElseThrow(IllegalArgumentException::new);
        // 2. 대출기록 정보를 확인해 대출중인지 확인.
        // 3. 만약에 확인했는데 대출중이라면 에러 발생
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException("이미 대출중인 책입니다.");
        }
        // 4. 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        // 5. 유저정보와 책 정보를 기반으로 대출기록 생성
        user.loanBook(request.getBookName());
    }

    @Transactional
    public void returnBook(BookReturnRequestDTO request) {
        // 1. 유저 이름과 책 이름을 기반으로 대출 기록 찾기
        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        // 2. 대출 기록이 없다면 에러 발생
        user.returnBook(request.getBookName());
    }
}
