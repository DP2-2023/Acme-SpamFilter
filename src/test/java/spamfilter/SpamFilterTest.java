
package spamfilter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpamFilterTest {

	private String spamTermsString;


	@Before
	public void setUp() throws Exception {
		this.spamTermsString = "sex,viagra,cialis,one million,you've won,nigeria";
	}

	@Test
	public void testIsSpam() {
		final SpamFilter spamFilter = new SpamFilter(this.spamTermsString, 0.30f);

		// Test input with all spam terms
		Assert.assertTrue(spamFilter.isSpam("You've won one million dollars in Nigeria!"));

		// Test input with some spam terms
		Assert.assertTrue(spamFilter.isSpam("Get cheap viagra and cialis now!"));

		// Test input with no spam terms
		Assert.assertFalse(spamFilter.isSpam("This is a legitimate email."));
	}

	@Test
	public void testIsSpamWithNormalizedInput() {
		final SpamFilter spamFilter = new SpamFilter(this.spamTermsString, 0.40f);

		// Test input with extra spaces and mixed case
		Assert.assertTrue(spamFilter.isSpam("  yOu'Ve WOn   OnE    mIllioN   In  NiGEriA!  "));

		// Test input with no spam terms and extra spaces
		Assert.assertFalse(spamFilter.isSpam("  This  is   a   legitimate  email.  "));
	}

	@Test
	public void testIsSpamWithLowThreshold() {
		final SpamFilter spamFilter = new SpamFilter(this.spamTermsString, 0.25f);

		// Test input with only one spam term
		Assert.assertFalse(spamFilter.isSpam("Get very cheap viagra now!"));

		// Test input with two spam terms
		Assert.assertTrue(spamFilter.isSpam("Get cheap viagra and one million dollars now!"));
	}
}
