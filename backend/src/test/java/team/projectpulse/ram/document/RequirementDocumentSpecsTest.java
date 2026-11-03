package team.projectpulse.ram.document;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RequirementDocumentSpecsTest {

    @Test
    @ResourceLock("java.util.Locale.default")
    @SuppressWarnings("unchecked")
    void titleSearchDoesNotDependOnServerLocale() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            Root<RequirementDocument> root = mock(Root.class);
            CriteriaQuery<?> query = mock(CriteriaQuery.class);
            CriteriaBuilder builder = mock(CriteriaBuilder.class);
            Expression<String> lowercaseTitle = mock(Expression.class);
            when(builder.lower(any())).thenReturn(lowercaseTitle);

            RequirementDocumentSpecs.hasTitleLike("TITLE").toPredicate(root, query, builder);

            verify(builder).like(lowercaseTitle, "%title%");
        } finally {
            Locale.setDefault(original);
        }
    }
}
