package ostmodern.skylark.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StringUtilsTest {
    @Test
    public void meaningfulPart() throws Exception {
        // Given
        String imageUrl = "https://skylark-qa-fixtures.s3.amazonaws.com/media/mages/stills/film/980/promo210350250.jpg?Signature=BmIAHfi6AaeqtWuL1TUWdnqz7Ug%3D&Expires=1511045479&AWSAccessKeyId=AKIAIAGQAAEZJZUE4JIA";

        // When
        String meaningfulPart = StringUtils.meaningfulPart(imageUrl);

        // Then
        assertThat(meaningfulPart, equalTo("https://skylark-qa-fixtures.s3.amazonaws.com/media/mages/stills/film/980/promo210350250.jpg"));
    }

}