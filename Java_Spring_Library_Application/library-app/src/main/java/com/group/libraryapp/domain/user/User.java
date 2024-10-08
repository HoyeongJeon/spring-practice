package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20) // name varchar(20)
    private String name;

    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // 연관관계 주인 설정
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {} // JPA에선 기본생성자가 꼭 필요하다!

    public User(String name, Integer age) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어 있습니다.", name));
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어 있습니다.", name));
        }
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this,bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories
                .stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
