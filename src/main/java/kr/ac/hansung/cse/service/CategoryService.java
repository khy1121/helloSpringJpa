package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(String name) {
        // 입력값의 앞뒤 공백을 제거해 중복 검사와 저장 기준을 통일한 기능을 위해 추가했습니다.
        String normalizedName = name == null ? null : name.trim();

        // 빈 문자열 입력을 사전에 차단해 잘못된 카테고리 생성을 막는 기능을 위해 추가했습니다.
        if (normalizedName == null || normalizedName.isBlank()) {
            throw new IllegalArgumentException("카테고리 이름은 필수 입력 항목입니다.");
        }

        // 카테고리 이름 중복 등록을 방지한 기능을 위해 추가했습니다.
        categoryRepository.findByName(normalizedName)
                .ifPresent(category -> {
                    throw new DuplicateCategoryException(normalizedName);
                });

        return categoryRepository.save(new Category(normalizedName));
    }

    @Transactional
    public void deleteCategory(Long id) {
        // 삭제 전에 연결된 상품 개수를 확인한 기능을 위해 추가했습니다.
        long count = categoryRepository.countProductsByCategoryId(id);

        if (count > 0) {
            // 연결 상품이 있을 때 삭제를 막아 데이터 정합성을 지키는 기능을 위해 추가했습니다.
            throw new IllegalStateException("상품 " + count + "개가 연결되어 있어 삭제할 수 없습니다.");
        }

        categoryRepository.delete(id);
    }
}
