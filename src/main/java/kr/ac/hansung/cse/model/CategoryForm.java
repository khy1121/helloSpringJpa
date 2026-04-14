package kr.ac.hansung.cse.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Spring MVC 폼 바인딩에서 기본 생성자가 필요해서 추가한 기능을 위해 추가했습니다.
public class CategoryForm {

    // 수정 시 어떤 카테고리인지 구분할 수 있도록 id를 담기 위한 기능을 위해 추가했습니다.
    private Long id;

    @NotBlank(message = "카테고리 이름을 입력하세요")
    @Size(max = 50, message = "50자 이내로 입력하세요")
    private String name;

    // 폼 데이터를 엔티티로 변환해서 저장 로직에서 재사용하기 위한 기능을 위해 추가했습니다.
    public Category toEntity() {
        return new Category(this.name);
    }

    // 엔티티 값을 수정 폼에 쉽게 채우기 위한 기능을 위해 추가했습니다.
    public static CategoryForm from(Category category) {
        CategoryForm form = new CategoryForm();
        form.id = category.getId();
        form.name = category.getName();
        return form;
    }
}
