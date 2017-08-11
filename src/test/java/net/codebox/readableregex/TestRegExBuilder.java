package net.codebox.readableregex;

import org.junit.Test;

import static net.codebox.readableregex.CharacterTokenPart.characters;
import static net.codebox.readableregex.CharacterTokenPart.range;
import static net.codebox.readableregex.Token.*;
import static net.codebox.readableregex.Quantifier.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class TestRegExBuilder {
    @Test
    public void whenEmptyBuilder_thenEmptyText(){
        assertThat(RegExBuilder.build(), is(""));
    }
    
    @Test
    public void whenOnlyPlainText_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(text("A")), is("A"));
        assertThat(RegExBuilder.build(text("ABC")), is("ABC"));
        assertThat(RegExBuilder.build(text("ABC"), text("123")), is("ABC123"));
    }
    
    @Test
    public void whenMatchAnything_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anything()), is(".*"));
        assertThat(RegExBuilder.build(anything(), anything()), is(".*.*"));
    }
    
    @Test
    public void whenMatchAnyDigit_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyDigit()), is("[0-9]"));
        assertThat(RegExBuilder.build(anyDigit(), anyDigit()), is("[0-9][0-9]"));
    }
    
    @Test
    public void whenMatchAnyLetter_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyLetter()), is("[a-zA-Z]"));
        assertThat(RegExBuilder.build(anyLetter(), anyLetter()), is("[a-zA-Z][a-zA-Z]"));
    }
    
 // 'One or More' quantifier
    @Test
    public void whenMatchUsesOneOrMoreQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(oneOrMore().of("x")), is("x+"));
        assertThat(RegExBuilder.build(oneOrMore().of(text("xyz"))), is("(xyz)+"));
        assertThat(RegExBuilder.build(oneOrMore().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)+"));
    }

    @Test
    public void whenMatchUsesOneOrMoreGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(oneOrMore().greedy().of("x")), is("x+"));
        assertThat(RegExBuilder.build(oneOrMore().greedy().of(text("xyz"))), is("(xyz)+"));
        assertThat(RegExBuilder.build(oneOrMore().greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)+"));
    }

    @Test
    public void whenMatchUsesOneOrMorePossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(oneOrMore().possessive().of("x")), is("x++"));
        assertThat(RegExBuilder.build(oneOrMore().possessive().of(text("xyz"))), is("(xyz)++"));
        assertThat(RegExBuilder.build(oneOrMore().possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)++"));
    }

    @Test
    public void whenMatchUsesOneOrMoreLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(oneOrMore().lazy().of("x")), is("x+?"));
        assertThat(RegExBuilder.build(oneOrMore().lazy().of(text("xyz"))), is("(xyz)+?"));
        assertThat(RegExBuilder.build(oneOrMore().lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)+?"));
    }

 // 'Zero or More' quantifier    
    @Test
    public void whenMatchUsesZeroOrMoreQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrMore().of("x")), is("x*"));
        assertThat(RegExBuilder.build(zeroOrMore().of(text("xyz"))), is("(xyz)*"));
        assertThat(RegExBuilder.build(zeroOrMore().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)*"));
    }

    @Test
    public void whenMatchUsesZeroOrMoreGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrMore().greedy().of("x")), is("x*"));
        assertThat(RegExBuilder.build(zeroOrMore().greedy().of(text("xyz"))), is("(xyz)*"));
        assertThat(RegExBuilder.build(zeroOrMore().greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)*"));
    }

    @Test
    public void whenMatchUsesZeroOrMorePossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrMore().possessive().of("x")), is("x*+"));
        assertThat(RegExBuilder.build(zeroOrMore().possessive().of(text("xyz"))), is("(xyz)*+"));
        assertThat(RegExBuilder.build(zeroOrMore().possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)*+"));
    }

    @Test
    public void whenMatchUsesZeroOrMoreLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrMore().lazy().of("x")), is("x*?"));
        assertThat(RegExBuilder.build(zeroOrMore().lazy().of(text("xyz"))), is("(xyz)*?"));
        assertThat(RegExBuilder.build(zeroOrMore().lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)*?"));
    }

 // 'Between' quantifier
    @Test(expected=IllegalArgumentException.class)
    public void whenNegativeFromValuePassedToBetweenMethod_thenExceptionIsThrown(){
        between(-1, 4);
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenNegativeToValuePassedToBetweenMethod_thenExceptionIsThrown(){
        between(1, -4);
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenFromValueLargerThanToValueIsPassedToBetweenMethod_thenExceptionIsThrown(){
        between(4, 1);
    }
    
    @Test
    public void whenMatchUsesBetweenQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(between(2, 4).of("x")), is("x{2,4}"));
        assertThat(RegExBuilder.build(between(2, 4).of(text("xyz"))), is("(xyz){2,4}"));
        assertThat(RegExBuilder.build(between(2, 4).of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){2,4}"));
    }

    @Test
    public void whenMatchUsesBetweenGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(between(2, 4).greedy().of("x")), is("x{2,4}"));
        assertThat(RegExBuilder.build(between(2, 4).greedy().of(text("xyz"))), is("(xyz){2,4}"));
        assertThat(RegExBuilder.build(between(2, 4).greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){2,4}"));
    }

    @Test
    public void whenMatchUsesBetweenPossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(between(2, 4).possessive().of("x")), is("x{2,4}+"));
        assertThat(RegExBuilder.build(between(2, 4).possessive().of(text("xyz"))), is("(xyz){2,4}+"));
        assertThat(RegExBuilder.build(between(2, 4).possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){2,4}+"));
        
    }

    @Test
    public void whenMatchUsesBetweenLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(between(2, 4).lazy().of("x")), is("x{2,4}?"));
        assertThat(RegExBuilder.build(between(2, 4).lazy().of(text("xyz"))), is("(xyz){2,4}?"));
        assertThat(RegExBuilder.build(between(2, 4).lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){2,4}?"));
    }

 // 'Zero or One' quantifier    
    @Test
    public void whenMatchUsesZeroOrOneQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrOne().of("x")), is("x?"));
        assertThat(RegExBuilder.build(zeroOrOne().of(text("xyz"))), is("(xyz)?"));
        assertThat(RegExBuilder.build(zeroOrOne().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)?"));
    }

    @Test
    public void whenMatchUsesZeroOrOneGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrOne().greedy().of("x")), is("x?"));
        assertThat(RegExBuilder.build(zeroOrOne().greedy().of(text("xyz"))), is("(xyz)?"));
        assertThat(RegExBuilder.build(zeroOrOne().greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)?"));
    }

    @Test
    public void whenMatchUsesZeroOrOnePossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrOne().possessive().of("x")), is("x?+"));
        assertThat(RegExBuilder.build(zeroOrOne().possessive().of(text("xyz"))), is("(xyz)?+"));
        assertThat(RegExBuilder.build(zeroOrOne().possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)?+"));
    }

    @Test
    public void whenMatchUsesZeroOrOneLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(zeroOrOne().lazy().of("x")), is("x??"));
        assertThat(RegExBuilder.build(zeroOrOne().lazy().of(text("xyz"))), is("(xyz)??"));
        assertThat(RegExBuilder.build(zeroOrOne().lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*)??"));
    }
    
 // 'Exactly' quantifier    
    @Test(expected=IllegalArgumentException.class)
    public void whenNegativeValuePassedToExactlyMethod_thenRegexIsCorrect(){
        exactly(-1);
    }
    
    @Test
    public void whenMatchUsesExactlyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(exactly(4).of("x")), is("x{4}"));
        assertThat(RegExBuilder.build(exactly(4).of(text("xyz"))), is("(xyz){4}"));
        assertThat(RegExBuilder.build(exactly(4).of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4}"));
    }

    @Test
    public void whenMatchUsesExactlyGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(exactly(4).greedy().of("x")), is("x{4}"));
        assertThat(RegExBuilder.build(exactly(4).greedy().of(text("xyz"))), is("(xyz){4}"));
        assertThat(RegExBuilder.build(exactly(4).greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4}"));
    }

    @Test
    public void whenMatchUsesExactlyPossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(exactly(4).possessive().of("x")), is("x{4}+"));
        assertThat(RegExBuilder.build(exactly(4).possessive().of(text("xyz"))), is("(xyz){4}+"));
        assertThat(RegExBuilder.build(exactly(4).possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4}+"));
    }

    @Test
    public void whenMatchUsesExactlyLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(exactly(4).lazy().of("x")), is("x{4}?"));
        assertThat(RegExBuilder.build(exactly(4).lazy().of(text("xyz"))), is("(xyz){4}?"));
        assertThat(RegExBuilder.build(exactly(4).lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4}?"));
    }
    
 // 'At Least' quantifier    
    @Test(expected=IllegalArgumentException.class)
    public void whenNegativeValuePassedToAtLeastMethod_thenRegexIsCorrect(){
        atLeast(-1);
    }
    
    @Test
    public void whenMatchUsesAtLeastQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(atLeast(4).of("x")), is("x{4,}"));
        assertThat(RegExBuilder.build(atLeast(4).of(text("xyz"))), is("(xyz){4,}"));
        assertThat(RegExBuilder.build(atLeast(4).of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4,}"));
    }

    @Test
    public void whenMatchUsesAtLeastGreedyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(atLeast(4).greedy().of("x")), is("x{4,}"));
        assertThat(RegExBuilder.build(atLeast(4).greedy().of(text("xyz"))), is("(xyz){4,}"));
        assertThat(RegExBuilder.build(atLeast(4).greedy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4,}"));
    }

    @Test
    public void whenMatchUsesAtLeastPossessiveQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(atLeast(4).possessive().of("x")), is("x{4,}+"));
        assertThat(RegExBuilder.build(atLeast(4).possessive().of(text("xyz"))), is("(xyz){4,}+"));
        assertThat(RegExBuilder.build(atLeast(4).possessive().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4,}+"));
    }

    @Test
    public void whenMatchUsesAtLeastLazyQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(atLeast(4).lazy().of("x")), is("x{4,}?"));
        assertThat(RegExBuilder.build(atLeast(4).lazy().of(text("xyz"))), is("(xyz){4,}?"));
        assertThat(RegExBuilder.build(atLeast(4).lazy().of(anyDigit(), text("xyz"), anything())), is("([0-9]xyz.*){4,}?"));
    }


    @Test
    public void whenOrOperatorUsedWithoutQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(or(text("A"), text("BCD"), text("E"))), is("A|BCD|E"));
        assertThat(RegExBuilder.build(or(text("A"))), is("A"));
        assertThat(RegExBuilder.build(or(text("ABC"))), is("ABC"));
    }

    @Test
    public void whenOrOperatorUsedWithQuantifier_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(exactly(4).of(or(text("ABC"), text("D"), text("E")))), is("(ABC|D|E){4}"));
        assertThat(RegExBuilder.build(exactly(4).of(or(text("A")))), is("A{4}"));
        assertThat(RegExBuilder.build(exactly(4).of(or(text("ABC")))), is("(ABC){4}"));
    }

    @Test
    public void whenOneOfOperatorUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(oneOf(text("ABC"), text("123"))), is("ABC|123"));
    }

    @Test
    public void whenMatchAnyOneCharacter_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a','e','i','o','u'))), is("[aeiou]"));
    }

    @Test
    public void whenMatchAnyRangeOfCharacters_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(range('a','e'), range('v','z'))), is("[a-ev-z]"));
    }

    @Test
    public void whenMatchAnyCharacterExcept_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyCharacterExcept(range('a','e'), characters('x','y','z'))), is("[^a-exyz]"));
    }

    @Test
    public void whenSpecialCharactersUsedInText_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(text("\\")), is("\\\\"));
        assertThat(RegExBuilder.build(text("[")),  is("\\["));
        assertThat(RegExBuilder.build(text("^")),  is("\\^"));
        assertThat(RegExBuilder.build(text("$")),  is("\\$"));
        assertThat(RegExBuilder.build(text(".")),  is("\\."));
        assertThat(RegExBuilder.build(text("|")),  is("\\|"));
        assertThat(RegExBuilder.build(text("?")),  is("\\?"));
        assertThat(RegExBuilder.build(text("*")),  is("\\*"));
        assertThat(RegExBuilder.build(text("+")),  is("\\+"));
        assertThat(RegExBuilder.build(text("(")),  is("\\("));
        assertThat(RegExBuilder.build(text(")")),  is("\\)"));
        assertThat(RegExBuilder.build(text("{")),  is("\\{"));
        assertThat(RegExBuilder.build(text("}")),  is("\\}"));
    }

    @Test
    public void whenSpecialCharactersUsedInCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('\\'))), is("[\\\\]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('-'))),  is("[-]"));
        assertThat(RegExBuilder.build(anyOneOf(characters(']'))),  is("[\\]]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('^'))),  is("[\\^]"));
        assertThat(RegExBuilder.build(anyOneOf(range('\\','-'))),  is("[\\\\-\\-]"));
        assertThat(RegExBuilder.build(anyOneOf(range('^',']'))),   is("[\\^-\\]]"));
    }

 // Literal Caret in character matcher
    @Test
    public void whenCaretUsedAtStartOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('^'))), is("[\\^]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('^', 'a', 'b'))), is("[\\^ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('^'), characters('a', 'b'))), is("[\\^ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('^', 'a'), characters('b'))), is("[\\^ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('^', 'a', 'b'), characters('x', 'y'), range('0', '9'))), is("[\\^abxy0-9]"));
    }

    @Test
    public void whenCaretUsedInMiddleOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', '^', 'b'))), is("[a^b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters('^', 'b'))), is("[a^b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', '^'), characters('b'))), is("[a^b]"));
    }

    @Test
    public void whenCaretUsedAtEndOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b', '^'))), is("[ab^]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters('b', '^'))), is("[ab^]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b'), characters('^'))), is("[ab^]"));
    }

 // Literal Closing Bracket in character matcher    
    @Test
    public void whenClosingBracketUsedAtStartOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters(']'))), is("[\\]]"));
        assertThat(RegExBuilder.build(anyOneOf(characters(']', 'a', 'b'))), is("[\\]ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters(']'), characters('a', 'b'))), is("[\\]ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters(']', 'a'), characters('b'))), is("[\\]ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters(']', 'a', 'b'), characters('x', 'y'), range('0', '9'))), is("[\\]abxy0-9]"));
    }

    @Test
    public void whenClosingBracketUsedInMiddleOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', ']', 'b'))), is("[a\\]b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters(']', 'b'))), is("[a\\]b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', ']'), characters('b'))), is("[a\\]b]"));
    }
    
    @Test
    public void whenClosingBracketUsedAtEndOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b', ']'))), is("[ab\\]]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters('b', ']'))), is("[ab\\]]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b'), characters(']'))), is("[ab\\]]"));
        
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('[', ']','\\'))), is("[^[\\]\\\\]"));
    }

 // Literal Closing Bracket in negative character matcher    
    @Test
    public void whenClosingBracketUsedAtStartOfNegativeCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyCharacterExcept(characters(']'))), is("[^\\]]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters(']', 'a', 'b'))), is("[^\\]ab]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters(']'), characters('a', 'b'))), is("[^\\]ab]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters(']', 'a'), characters('b'))), is("[^\\]ab]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters(']', 'a', 'b'), characters('x', 'y'), range('0', '9'))), is("[^\\]abxy0-9]"));
    }

    @Test
    public void whenClosingBracketUsedInMiddleOfNegativeCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a', ']', 'b'))), is("[^a\\]b]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a'), characters(']', 'b'))), is("[^a\\]b]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a', ']'), characters('b'))), is("[^a\\]b]"));
    }

    @Test
    public void whenClosingBracketUsedAtEndOfNegativeCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a', 'b', ']'))), is("[^ab\\]]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a'), characters('b', ']'))), is("[^ab\\]]"));
        assertThat(RegExBuilder.build(anyCharacterExcept(characters('a', 'b'), characters(']'))), is("[^ab\\]]"));

    }

 // Literal Hyphen in character matcher        
    @Test
    public void whenHyphenUsedAtStartOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('-'))), is("[-]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('-', 'a', 'b'))), is("[-ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('-'), characters('a', 'b'))), is("[-ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('-', 'a'), characters('b'))), is("[-ab]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('-', 'a', 'b'), characters('x', 'y'), range('0', '9'))), is("[-abxy0-9]"));
    }

    @Test
    public void whenHyphenUsedInMiddleOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', '-', 'b'))), is("[a\\-b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters('-', 'b'))), is("[a\\-b]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', '-'), characters('b'))), is("[a\\-b]"));
    }

    @Test
    public void whenHyphenUsedAtEndOfCharacterMatcher_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b', '-'))), is("[ab\\-]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a'), characters('b', '-'))), is("[ab\\-]"));
        assertThat(RegExBuilder.build(anyOneOf(characters('a', 'b'), characters('-'))), is("[ab\\-]"));
    }
    
    @Test
    public void whenTokensAreGrouped_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(groupOf(text("ABC"))), is("(ABC)"));
        assertThat(RegExBuilder.build(groupOf(text("ABC"), anyDigit(), text("XYZ"))), is("(ABC[0-9]XYZ)"));
    }

    @Test
    public void whenBackReferencesToGroupsAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(group(1)), is("\\1"));
        assertThat(RegExBuilder.build(text("A"), group(1), group(2), text("B")), is("A\\1\\2B"));
    }

    @Test
    public void whenNonCapturingGroupsAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(nonCapturingGroup(text("ABC"))), is("(?:ABC)"));
        assertThat(RegExBuilder.build(nonCapturingGroup(text("ABC"), anyDigit(), text("XYZ"))), is("(?:ABC[0-9]XYZ)"));
    }

    @Test
    public void whenIndependentNonCapturingGroupsAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(independentNonCapturingGroup(text("ABC"))), is("(?>ABC)"));
        assertThat(RegExBuilder.build(independentNonCapturingGroup(text("ABC"), anyDigit(), text("XYZ"))), is("(?>ABC[0-9]XYZ)"));
    }

    @Test
    public void whenPositiveLookAheadIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(positiveLookAhead(text("ABC"))), is("(?=ABC)"));
        assertThat(RegExBuilder.build(positiveLookAhead(text("ABC"), anyDigit(), text("XYZ"))), is("(?=ABC[0-9]XYZ)"));
    }

    @Test
    public void whenNegativeLookAheadIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(negativeLookAhead(text("ABC"))), is("(?!ABC)"));
        assertThat(RegExBuilder.build(negativeLookAhead(text("ABC"), anyDigit(), text("XYZ"))), is("(?!ABC[0-9]XYZ)"));
    }

    @Test
    public void whenPositiveLookBehindIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(positiveLookBehind(text("ABC"))), is("(?<=ABC)"));
        assertThat(RegExBuilder.build(positiveLookBehind(text("ABC"), anyDigit(), text("XYZ"))), is("(?<=ABC[0-9]XYZ)"));
    }

    @Test
    public void whenNegativeLookBehindIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(negativeLookBehind(text("ABC"))), is("(?<!ABC)"));
        assertThat(RegExBuilder.build(negativeLookBehind(text("ABC"), anyDigit(), text("XYZ"))), is("(?<!ABC[0-9]XYZ)"));
    }

    @Test
    public void whenValidOctalCharactersAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(octalCharacter("000")), is("\\0000"));
        assertThat(RegExBuilder.build(octalCharacter("277")), is("\\0277"));
        assertThat(RegExBuilder.build(octalCharacter("123")), is("\\0123"));
        assertThat(RegExBuilder.build(octalCharacter("77")), is("\\077"));
        assertThat(RegExBuilder.build(octalCharacter("00")), is("\\000"));
        assertThat(RegExBuilder.build(octalCharacter("7")), is("\\07"));
        assertThat(RegExBuilder.build(octalCharacter("0")), is("\\00"));
    }
    
    @Test
    public void whenInvalidOctalCharactersAreUsed_thenExceptionIsThrown(){
        checkBadOctalValue("300");
        checkBadOctalValue("080");
        checkBadOctalValue("008");
        checkBadOctalValue("00x");
        checkBadOctalValue("0000");
        checkBadOctalValue("");
    }

    private void checkBadOctalValue(String txt){
        try{
            RegExBuilder.build(octalCharacter(txt));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(), is("Bad octal value"));
        }
    }

    @Test
    public void whenValidHexCharactersAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(hexCharacter("00")), is("\\x00"));
        assertThat(RegExBuilder.build(hexCharacter("ff")), is("\\xFF"));
        assertThat(RegExBuilder.build(hexCharacter("1a")), is("\\x1A"));
        assertThat(RegExBuilder.build(hexCharacter("0000")), is("\\x0000"));
        assertThat(RegExBuilder.build(hexCharacter("fFFf")), is("\\xFFFF"));
    }
    
    @Test
    public void whenInvalidHexCharactersAreUsed_thenExceptionIsThrown(){
        checkBadHexValue("00000");
        checkBadHexValue("000");
        checkBadHexValue("0");
        checkBadHexValue("G0");
        checkBadHexValue("0G");
        checkBadHexValue("");
    }

    private void checkBadHexValue(String txt){
        try{
            RegExBuilder.build(hexCharacter(txt));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(), is("Bad hex value"));
        }
    }
    
    @Test
    public void whenValidUnicodeCharactersAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(unicodeCharacter("0000")), is("\\u0000"));
        assertThat(RegExBuilder.build(unicodeCharacter("ffFF")), is("\\uFFFF"));
        assertThat(RegExBuilder.build(unicodeCharacter("12aB")), is("\\u12AB"));
    }
    
    @Test
    public void whenInvalidUnicodeCharactersAreUsed_thenExceptionIsThrown(){
        checkBadUnicodeValue("00000");
        checkBadUnicodeValue("000");
        checkBadUnicodeValue("00");
        checkBadUnicodeValue("0");
        checkBadUnicodeValue("");
        checkBadUnicodeValue("G000");
        checkBadUnicodeValue("0G00");
        checkBadUnicodeValue("00G0");
        checkBadUnicodeValue("000G");
    }

    private void checkBadUnicodeValue(String txt){
        try{
            RegExBuilder.build(unicodeCharacter(txt));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(), is("Bad unicode value"));
        }
    }
    
    @Test
    public void whenTabCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(tab()), is("\\t"));
        assertThat(RegExBuilder.build(text("A"), tab(), tab(), text("B")), is("A\\t\\tB"));
    }
    
    @Test
    public void whenNewlineCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(newline()), is("\\n"));
        assertThat(RegExBuilder.build(text("A"), newline(), newline(), text("B")), is("A\\n\\nB"));
    }
    
    @Test
    public void whenCarriageReturnCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(carriageReturn()), is("\\r"));
        assertThat(RegExBuilder.build(text("A"), carriageReturn(), carriageReturn(), text("B")), is("A\\r\\rB"));
    }

    @Test
    public void whenFormFeedCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(formFeed()), is("\\f"));
        assertThat(RegExBuilder.build(text("A"), formFeed(), formFeed(), text("B")), is("A\\f\\fB"));
    }

    @Test
    public void whenAlertCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(alertCharacter()), is("\\a"));
        assertThat(RegExBuilder.build(text("A"), alertCharacter(), alertCharacter(), text("B")), is("A\\a\\aB"));
    }
    
    @Test
    public void whenEscapeCharacterIsUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(escapeCharacter()), is("\\e"));
        assertThat(RegExBuilder.build(text("A"), escapeCharacter(), escapeCharacter(), text("B")), is("A\\e\\eB"));
    }
    
    @Test
    public void whenValidControlCharactersAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(controlCharacter('A')), is("\\cA"));
        assertThat(RegExBuilder.build(controlCharacter('z')), is("\\cZ"));
    }
    
    @Test
    public void whenInvalidControlCharactersAreUsed_thenExceptionIsThrown(){
        checkBadControlValue('1');
        checkBadControlValue('\\');
        checkBadControlValue('\0');
    }

    private void checkBadControlValue(char txt){
        try{
            RegExBuilder.build(controlCharacter(txt));
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(), is("Only letters are permitted for control characters"));
        }
    }
    
    @Test
    public void whenMultipleArgumentsAreUsed_thenRegexIsCorrect(){
        assertThat(RegExBuilder.build(
            oneOrMore().of(
                anyOneOf(
                    characters('_','-'), range('A','Z'), range('a','z'), range('0','9')
                )
            ),
            zeroOrMore().of(
                text("."),
                oneOrMore().of(
                    anyOneOf(
                        characters('_','-'), range('A','Z'), range('a','z'), range('0','9')
                    )
                )
            ),
            text("@"),
            oneOrMore().of(
                anyOneOf(
                    range('A','Z'), range('a','z'), range('0','9')
                )
            ),
            zeroOrMore().of(
                text("."),
                oneOrMore().of(
                    anyOneOf(
                        range('A','Z'), range('a','z'), range('0','9')
                    )
                )
            ),
            text("."),
            atLeast(2).of(
                anyLetter()
            )
        ), is("[_\\-A-Za-z0-9]+(\\.[_\\-A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*\\.[a-zA-Z]{2,}"));
    }
}
