package com.example.wanted.jobopening.repository.custom;

import static com.example.wanted.jobopening.domain.QJobOpening.jobOpening;
import static com.example.wanted.jobopening.domain.QUsingStack.usingStack;

import com.example.wanted.jobopening.domain.JobOpening;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class JobOpeningRepositoryImpl implements JobOpeningRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<JobOpening> searchJopOpeningsBySearchText(String searchText, Pageable pageable) {
		List<JobOpening> content = jpaQueryFactory
				.select(jobOpening).distinct()
				.from(jobOpening)
				.leftJoin(jobOpening.usingStacks, usingStack).fetchJoin()
				.where(searchTextCondition(searchText))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> count = jpaQueryFactory
				.select(jobOpening.count())
				.where(searchTextCondition(searchText));

		return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
	}

	private BooleanExpression companyNameLike(String searchText) {
		return searchText == null ? null : jobOpening.company.companyName.like("%" + searchText + "%");
	}

	private BooleanExpression positionLike(String searchText) {
		return searchText == null ? null : jobOpening.position.like("%" + searchText + "%");
	}

	private BooleanExpression usingStackLike(String searchText) {
		return searchText == null ? null : usingStack.stackName.like("%" + searchText + "%");
	}

	private BooleanBuilder searchTextCondition(String searchText) {
		BooleanBuilder builder = new BooleanBuilder();
		BooleanExpression companyNameLike = companyNameLike(searchText);
		BooleanExpression positionLike = positionLike(searchText);
		BooleanExpression usingStackLike = usingStackLike(searchText);

		return builder.and(companyNameLike.or(positionLike).or(usingStackLike));
	}
}
