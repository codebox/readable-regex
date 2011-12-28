Readable Regex
==============

This library provides a way to make complex regular expressions in Java code more readable. 

The best way to explain what it does to show some examples:

     // Matches a single digit
        RegExBuilder.build(anyDigit()); // "[0-9]"
    
     // Matches exactly 2 digits
    	RegExBuilder.build(exactly(2).of(anyDigit())); // "[0-9]{2}"
	
     // Matches between 2 and 4 letters
    	RegExBuilder.build(between(2,4).of(anyLetter())); // "[a-zA-Z]{2,4}"

Characters that have special meaning within a regular expression are escaped automatically:

     // Matches one or more occurrences of the text 'Ho?'
    	RegExBuilder.build(oneOrMore().of("Ho?")); // "(Ho\?)+"
    	
     // Matches anything except an opening or closing square bracket, or a backslash
    	RegExBuilder.build(
    		anyCharacterExcept(
    			characters('[', ']','\\')
    		)
    	); // "[^[\\]\\\\]"

Readability is greatly improved for more complex expressions:

     // More or less validates the format of an email address
     // [_\\-A-Za-z0-9]+(\\.[_\\-A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*\\.[a-zA-Z]{2,}
	    RegExBuilder.build(
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
	    );

All classes in the library are immutable, and therefore instances are re-usable and thread-safe.