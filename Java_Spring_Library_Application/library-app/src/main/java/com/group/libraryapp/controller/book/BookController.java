package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.BookCreateRequestDTO;
import com.group.libraryapp.dto.book.request.BookLoanRequestDTO;
import com.group.libraryapp.dto.book.request.BookReturnRequestDTO;
import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void saveBook(@RequestBody BookCreateRequestDTO request) {
        bookService.saveBook(request);
    }

    @PostMapping("/loan")
    public void loanBook(@RequestBody BookLoanRequestDTO request) {
        bookService.loanBook(request);
    }

    @PutMapping("/return")
    public void returnBook(@RequestBody BookReturnRequestDTO request) {
        bookService.returnBook(request);
    }
}
