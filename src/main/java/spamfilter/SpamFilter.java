
package spamfilter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SpamFilter {

	private final Set<String>	spamTerms;
	private final float			threshold;


	public SpamFilter(final String spamTermsString, final float threshold) {
		super();
		this.spamTerms = Arrays.stream(spamTermsString.split(",")).map(x -> x.toLowerCase().replaceAll("\\s+", "")).collect(Collectors.toSet());
		this.threshold = threshold;
	}

	public Boolean isSpam(final String inputString) {
		final String normalizedInput = inputString.toLowerCase().replaceAll("\\s+", "");
		final int nInputWords = inputString.split("\\s+").length;

		int numSpamTerms = 0;
		for (final String spamTerm : this.spamTerms)
			numSpamTerms += SpamFilter.countMatches(normalizedInput, spamTerm);

		final float spamRatio = (float) numSpamTerms / nInputWords;

		return spamRatio >= this.threshold;
	}

	private static int countMatches(final String str, final String substr) {
		int count = 0;
		int index = 0;
		while ((index = str.indexOf(substr, index)) != -1) {
			count++;
			index += substr.length();
		}
		return count;
	}
}
