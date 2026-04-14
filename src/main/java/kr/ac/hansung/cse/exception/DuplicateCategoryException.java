package kr.ac.hansung.cse.exception;

public class DuplicateCategoryException extends RuntimeException {

    // 카테고리 이름 중복 상황을 명확한 메시지로 전달한 기능을 위해 추가했습니다.
    public DuplicateCategoryException(String name) {
        super("이미 존재하는 카테고리입니다: " + name);
    }
}
