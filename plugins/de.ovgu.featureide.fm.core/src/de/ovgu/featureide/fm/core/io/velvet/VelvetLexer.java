// $ANTLR 3.4 Velvet.g 2015-09-20 21:45:06
package de.ovgu.featureide.fm.core.io.velvet;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class VelvetLexer extends Lexer {
    public static final int EOF=-1;
    public static final int ABSTRACT=4;
    public static final int ACONSTR=5;
    public static final int ATTR=6;
    public static final int ATTR_OP_EQUALS=7;
    public static final int ATTR_OP_GREATER=8;
    public static final int ATTR_OP_GREATER_EQ=9;
    public static final int ATTR_OP_LESS=10;
    public static final int ATTR_OP_LESS_EQ=11;
    public static final int ATTR_OP_NOT_EQUALS=12;
    public static final int BASEEXT=13;
    public static final int BOOLEAN=14;
    public static final int CINTERFACE=15;
    public static final int COLON=16;
    public static final int COMMA=17;
    public static final int CONCEPT=18;
    public static final int CONSTR=19;
    public static final int CONSTRAINT=20;
    public static final int DEF=21;
    public static final int DESCRIPTION=22;
    public static final int EMPTY=23;
    public static final int END_C=24;
    public static final int END_R=25;
    public static final int EQ=26;
    public static final int ESC_SEQ=27;
    public static final int EXPONENT=28;
    public static final int FEATURE=29;
    public static final int FLOAT=30;
    public static final int GROUP=31;
    public static final int HEX_DIGIT=32;
    public static final int ID=33;
    public static final int IDPath=34;
    public static final int IMPORTINSTANCE=35;
    public static final int IMPORTINTERFACE=36;
    public static final int INT=37;
    public static final int MANDATORY=38;
    public static final int MINUS=39;
    public static final int ML_COMMENT=40;
    public static final int OCTAL_ESC=41;
    public static final int ONEOF=42;
    public static final int OPERAND=43;
    public static final int OP_AND=44;
    public static final int OP_EQUIVALENT=45;
    public static final int OP_IMPLIES=46;
    public static final int OP_NOT=47;
    public static final int OP_OR=48;
    public static final int OP_XOR=49;
    public static final int PLUS=50;
    public static final int SEMI=51;
    public static final int SL_COMMENT=52;
    public static final int SOMEOF=53;
    public static final int START_C=54;
    public static final int START_R=55;
    public static final int STRING=56;
    public static final int UNARYOP=57;
    public static final int UNICODE_ESC=58;
    public static final int USE=59;
    public static final int VAR_BOOL=60;
    public static final int VAR_FLOAT=61;
    public static final int VAR_INT=62;
    public static final int VAR_STRING=63;
    public static final int WS=64;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public VelvetLexer() {} 
    public VelvetLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public VelvetLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "Velvet.g"; }

    // $ANTLR start "ABSTRACT"
    public final void mABSTRACT() throws RecognitionException {
        try {
            int _type = ABSTRACT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:4:10: ( 'abstract' )
            // Velvet.g:4:12: 'abstract'
            {
            match("abstract"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ABSTRACT"

    // $ANTLR start "ATTR_OP_EQUALS"
    public final void mATTR_OP_EQUALS() throws RecognitionException {
        try {
            int _type = ATTR_OP_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:5:16: ( '==' )
            // Velvet.g:5:18: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_EQUALS"

    // $ANTLR start "ATTR_OP_GREATER"
    public final void mATTR_OP_GREATER() throws RecognitionException {
        try {
            int _type = ATTR_OP_GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:6:17: ( '>' )
            // Velvet.g:6:19: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_GREATER"

    // $ANTLR start "ATTR_OP_GREATER_EQ"
    public final void mATTR_OP_GREATER_EQ() throws RecognitionException {
        try {
            int _type = ATTR_OP_GREATER_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:7:20: ( '>=' )
            // Velvet.g:7:22: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_GREATER_EQ"

    // $ANTLR start "ATTR_OP_LESS"
    public final void mATTR_OP_LESS() throws RecognitionException {
        try {
            int _type = ATTR_OP_LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:8:14: ( '<' )
            // Velvet.g:8:16: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_LESS"

    // $ANTLR start "ATTR_OP_LESS_EQ"
    public final void mATTR_OP_LESS_EQ() throws RecognitionException {
        try {
            int _type = ATTR_OP_LESS_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:9:17: ( '<=' )
            // Velvet.g:9:19: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_LESS_EQ"

    // $ANTLR start "ATTR_OP_NOT_EQUALS"
    public final void mATTR_OP_NOT_EQUALS() throws RecognitionException {
        try {
            int _type = ATTR_OP_NOT_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:10:20: ( '!=' )
            // Velvet.g:10:22: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTR_OP_NOT_EQUALS"

    // $ANTLR start "CINTERFACE"
    public final void mCINTERFACE() throws RecognitionException {
        try {
            int _type = CINTERFACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:11:12: ( 'cinterface' )
            // Velvet.g:11:14: 'cinterface'
            {
            match("cinterface"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CINTERFACE"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:12:7: ( ':' )
            // Velvet.g:12:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:13:7: ( ',' )
            // Velvet.g:13:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "CONCEPT"
    public final void mCONCEPT() throws RecognitionException {
        try {
            int _type = CONCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:14:9: ( 'concept' )
            // Velvet.g:14:11: 'concept'
            {
            match("concept"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONCEPT"

    // $ANTLR start "CONSTRAINT"
    public final void mCONSTRAINT() throws RecognitionException {
        try {
            int _type = CONSTRAINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:15:12: ( 'constraint' )
            // Velvet.g:15:14: 'constraint'
            {
            match("constraint"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONSTRAINT"

    // $ANTLR start "DESCRIPTION"
    public final void mDESCRIPTION() throws RecognitionException {
        try {
            int _type = DESCRIPTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:16:13: ( 'description' )
            // Velvet.g:16:15: 'description'
            {
            match("description"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DESCRIPTION"

    // $ANTLR start "END_C"
    public final void mEND_C() throws RecognitionException {
        try {
            int _type = END_C;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:17:7: ( '}' )
            // Velvet.g:17:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "END_C"

    // $ANTLR start "END_R"
    public final void mEND_R() throws RecognitionException {
        try {
            int _type = END_R;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:18:7: ( ')' )
            // Velvet.g:18:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "END_R"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:19:4: ( '=' )
            // Velvet.g:19:6: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "FEATURE"
    public final void mFEATURE() throws RecognitionException {
        try {
            int _type = FEATURE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:20:9: ( 'feature' )
            // Velvet.g:20:11: 'feature'
            {
            match("feature"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FEATURE"

    // $ANTLR start "IMPORTINSTANCE"
    public final void mIMPORTINSTANCE() throws RecognitionException {
        try {
            int _type = IMPORTINSTANCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:21:16: ( 'instance' )
            // Velvet.g:21:18: 'instance'
            {
            match("instance"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IMPORTINSTANCE"

    // $ANTLR start "IMPORTINTERFACE"
    public final void mIMPORTINTERFACE() throws RecognitionException {
        try {
            int _type = IMPORTINTERFACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:22:17: ( 'interface' )
            // Velvet.g:22:19: 'interface'
            {
            match("interface"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IMPORTINTERFACE"

    // $ANTLR start "MANDATORY"
    public final void mMANDATORY() throws RecognitionException {
        try {
            int _type = MANDATORY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:23:11: ( 'mandatory' )
            // Velvet.g:23:13: 'mandatory'
            {
            match("mandatory"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MANDATORY"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:24:7: ( '-' )
            // Velvet.g:24:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "ONEOF"
    public final void mONEOF() throws RecognitionException {
        try {
            int _type = ONEOF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:25:7: ( 'oneOf' )
            // Velvet.g:25:9: 'oneOf'
            {
            match("oneOf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ONEOF"

    // $ANTLR start "OP_AND"
    public final void mOP_AND() throws RecognitionException {
        try {
            int _type = OP_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:26:8: ( '&&' )
            // Velvet.g:26:10: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_AND"

    // $ANTLR start "OP_EQUIVALENT"
    public final void mOP_EQUIVALENT() throws RecognitionException {
        try {
            int _type = OP_EQUIVALENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:27:15: ( '<->' )
            // Velvet.g:27:17: '<->'
            {
            match("<->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_EQUIVALENT"

    // $ANTLR start "OP_IMPLIES"
    public final void mOP_IMPLIES() throws RecognitionException {
        try {
            int _type = OP_IMPLIES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:28:12: ( '->' )
            // Velvet.g:28:14: '->'
            {
            match("->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_IMPLIES"

    // $ANTLR start "OP_NOT"
    public final void mOP_NOT() throws RecognitionException {
        try {
            int _type = OP_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:29:8: ( '!' )
            // Velvet.g:29:10: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_NOT"

    // $ANTLR start "OP_OR"
    public final void mOP_OR() throws RecognitionException {
        try {
            int _type = OP_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:30:7: ( '||' )
            // Velvet.g:30:9: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_OR"

    // $ANTLR start "OP_XOR"
    public final void mOP_XOR() throws RecognitionException {
        try {
            int _type = OP_XOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:31:8: ( 'xor' )
            // Velvet.g:31:10: 'xor'
            {
            match("xor"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OP_XOR"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:32:6: ( '+' )
            // Velvet.g:32:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:33:6: ( ';' )
            // Velvet.g:33:8: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "SOMEOF"
    public final void mSOMEOF() throws RecognitionException {
        try {
            int _type = SOMEOF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:34:8: ( 'someOf' )
            // Velvet.g:34:10: 'someOf'
            {
            match("someOf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SOMEOF"

    // $ANTLR start "START_C"
    public final void mSTART_C() throws RecognitionException {
        try {
            int _type = START_C;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:35:9: ( '{' )
            // Velvet.g:35:11: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "START_C"

    // $ANTLR start "START_R"
    public final void mSTART_R() throws RecognitionException {
        try {
            int _type = START_R;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:36:9: ( '(' )
            // Velvet.g:36:11: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "START_R"

    // $ANTLR start "USE"
    public final void mUSE() throws RecognitionException {
        try {
            int _type = USE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:37:5: ( 'use' )
            // Velvet.g:37:7: 'use'
            {
            match("use"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "USE"

    // $ANTLR start "VAR_BOOL"
    public final void mVAR_BOOL() throws RecognitionException {
        try {
            int _type = VAR_BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:38:10: ( 'bool' )
            // Velvet.g:38:12: 'bool'
            {
            match("bool"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VAR_BOOL"

    // $ANTLR start "VAR_FLOAT"
    public final void mVAR_FLOAT() throws RecognitionException {
        try {
            int _type = VAR_FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:39:11: ( 'float' )
            // Velvet.g:39:13: 'float'
            {
            match("float"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VAR_FLOAT"

    // $ANTLR start "VAR_INT"
    public final void mVAR_INT() throws RecognitionException {
        try {
            int _type = VAR_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:40:9: ( 'int' )
            // Velvet.g:40:11: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VAR_INT"

    // $ANTLR start "VAR_STRING"
    public final void mVAR_STRING() throws RecognitionException {
        try {
            int _type = VAR_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:41:12: ( 'string' )
            // Velvet.g:41:14: 'string'
            {
            match("string"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VAR_STRING"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:219:9: ( 'true' | 'false' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='t') ) {
                alt1=1;
            }
            else if ( (LA1_0=='f') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // Velvet.g:219:11: 'true'
                    {
                    match("true"); 



                    }
                    break;
                case 2 :
                    // Velvet.g:220:4: 'false'
                    {
                    match("false"); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:223:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )* )
            // Velvet.g:223:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )*
            {
            if ( input.LA(1)=='-'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // Velvet.g:223:35: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='-'||(LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Velvet.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "IDPath"
    public final void mIDPath() throws RecognitionException {
        try {
            int _type = IDPath;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:226:8: ( ID ( '.' ID )+ )
            // Velvet.g:226:10: ID ( '.' ID )+
            {
            mID(); 


            // Velvet.g:226:13: ( '.' ID )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='.') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Velvet.g:226:14: '.' ID
            	    {
            	    match('.'); 

            	    mID(); 


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDPath"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:229:5: ( ( '0' .. '9' )+ )
            // Velvet.g:229:7: ( '0' .. '9' )+
            {
            // Velvet.g:229:7: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Velvet.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:233:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
            int alt11=3;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // Velvet.g:233:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // Velvet.g:233:9: ( '0' .. '9' )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // Velvet.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    match('.'); 

                    // Velvet.g:233:25: ( '0' .. '9' )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // Velvet.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    // Velvet.g:233:37: ( EXPONENT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='E'||LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // Velvet.g:233:37: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // Velvet.g:234:9: '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    match('.'); 

                    // Velvet.g:234:13: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // Velvet.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


                    // Velvet.g:234:25: ( EXPONENT )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='E'||LA9_0=='e') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // Velvet.g:234:25: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // Velvet.g:235:9: ( '0' .. '9' )+ EXPONENT
                    {
                    // Velvet.g:235:9: ( '0' .. '9' )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // Velvet.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);


                    mEXPONENT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:239:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
            // Velvet.g:239:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 

            // Velvet.g:239:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\\') ) {
                    alt12=1;
                }
                else if ( ((LA12_0 >= '\u0000' && LA12_0 <= '!')||(LA12_0 >= '#' && LA12_0 <= '[')||(LA12_0 >= ']' && LA12_0 <= '\uFFFF')) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // Velvet.g:239:14: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // Velvet.g:239:24: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // Velvet.g:244:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // Velvet.g:244:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // Velvet.g:244:22: ( '+' | '-' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='+'||LA13_0=='-') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // Velvet.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            // Velvet.g:244:33: ( '0' .. '9' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0 >= '0' && LA14_0 <= '9')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // Velvet.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // Velvet.g:247:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // Velvet.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // Velvet.g:251:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt15=3;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt15=1;
                    }
                    break;
                case 'u':
                    {
                    alt15=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt15=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // Velvet.g:251:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 

                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // Velvet.g:252:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // Velvet.g:253:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // Velvet.g:258:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt16=3;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='\\') ) {
                int LA16_1 = input.LA(2);

                if ( ((LA16_1 >= '0' && LA16_1 <= '3')) ) {
                    int LA16_2 = input.LA(3);

                    if ( ((LA16_2 >= '0' && LA16_2 <= '7')) ) {
                        int LA16_4 = input.LA(4);

                        if ( ((LA16_4 >= '0' && LA16_4 <= '7')) ) {
                            alt16=1;
                        }
                        else {
                            alt16=2;
                        }
                    }
                    else {
                        alt16=3;
                    }
                }
                else if ( ((LA16_1 >= '4' && LA16_1 <= '7')) ) {
                    int LA16_3 = input.LA(3);

                    if ( ((LA16_3 >= '0' && LA16_3 <= '7')) ) {
                        alt16=2;
                    }
                    else {
                        alt16=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // Velvet.g:258:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // Velvet.g:259:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // Velvet.g:260:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // Velvet.g:265:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // Velvet.g:265:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 

            match('u'); 

            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNICODE_ESC"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:267:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // Velvet.g:267:7: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "SL_COMMENT"
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:274:12: ( ( '//' (~ ( '\\r' | '\\n' ) )* ) )
            // Velvet.g:274:14: ( '//' (~ ( '\\r' | '\\n' ) )* )
            {
            // Velvet.g:274:14: ( '//' (~ ( '\\r' | '\\n' ) )* )
            // Velvet.g:274:15: '//' (~ ( '\\r' | '\\n' ) )*
            {
            match("//"); 



            // Velvet.g:274:20: (~ ( '\\r' | '\\n' ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0 >= '\u0000' && LA17_0 <= '\t')||(LA17_0 >= '\u000B' && LA17_0 <= '\f')||(LA17_0 >= '\u000E' && LA17_0 <= '\uFFFF')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // Velvet.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SL_COMMENT"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Velvet.g:276:12: ( ( '/*' (~ ( '*/' ) )* ) )
            // Velvet.g:276:14: ( '/*' (~ ( '*/' ) )* )
            {
            // Velvet.g:276:14: ( '/*' (~ ( '*/' ) )* )
            // Velvet.g:276:15: '/*' (~ ( '*/' ) )*
            {
            match("/*"); 



            // Velvet.g:276:20: (~ ( '*/' ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0 >= '\u0000' && LA18_0 <= '\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // Velvet.g:276:20: ~ ( '*/' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ML_COMMENT"

    public void mTokens() throws RecognitionException {
        // Velvet.g:1:8: ( ABSTRACT | ATTR_OP_EQUALS | ATTR_OP_GREATER | ATTR_OP_GREATER_EQ | ATTR_OP_LESS | ATTR_OP_LESS_EQ | ATTR_OP_NOT_EQUALS | CINTERFACE | COLON | COMMA | CONCEPT | CONSTRAINT | DESCRIPTION | END_C | END_R | EQ | FEATURE | IMPORTINSTANCE | IMPORTINTERFACE | MANDATORY | MINUS | ONEOF | OP_AND | OP_EQUIVALENT | OP_IMPLIES | OP_NOT | OP_OR | OP_XOR | PLUS | SEMI | SOMEOF | START_C | START_R | USE | VAR_BOOL | VAR_FLOAT | VAR_INT | VAR_STRING | BOOLEAN | ID | IDPath | INT | FLOAT | STRING | WS | SL_COMMENT | ML_COMMENT )
        int alt19=47;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // Velvet.g:1:10: ABSTRACT
                {
                mABSTRACT(); 


                }
                break;
            case 2 :
                // Velvet.g:1:19: ATTR_OP_EQUALS
                {
                mATTR_OP_EQUALS(); 


                }
                break;
            case 3 :
                // Velvet.g:1:34: ATTR_OP_GREATER
                {
                mATTR_OP_GREATER(); 


                }
                break;
            case 4 :
                // Velvet.g:1:50: ATTR_OP_GREATER_EQ
                {
                mATTR_OP_GREATER_EQ(); 


                }
                break;
            case 5 :
                // Velvet.g:1:69: ATTR_OP_LESS
                {
                mATTR_OP_LESS(); 


                }
                break;
            case 6 :
                // Velvet.g:1:82: ATTR_OP_LESS_EQ
                {
                mATTR_OP_LESS_EQ(); 


                }
                break;
            case 7 :
                // Velvet.g:1:98: ATTR_OP_NOT_EQUALS
                {
                mATTR_OP_NOT_EQUALS(); 


                }
                break;
            case 8 :
                // Velvet.g:1:117: CINTERFACE
                {
                mCINTERFACE(); 


                }
                break;
            case 9 :
                // Velvet.g:1:128: COLON
                {
                mCOLON(); 


                }
                break;
            case 10 :
                // Velvet.g:1:134: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 11 :
                // Velvet.g:1:140: CONCEPT
                {
                mCONCEPT(); 


                }
                break;
            case 12 :
                // Velvet.g:1:148: CONSTRAINT
                {
                mCONSTRAINT(); 


                }
                break;
            case 13 :
                // Velvet.g:1:159: DESCRIPTION
                {
                mDESCRIPTION(); 


                }
                break;
            case 14 :
                // Velvet.g:1:171: END_C
                {
                mEND_C(); 


                }
                break;
            case 15 :
                // Velvet.g:1:177: END_R
                {
                mEND_R(); 


                }
                break;
            case 16 :
                // Velvet.g:1:183: EQ
                {
                mEQ(); 


                }
                break;
            case 17 :
                // Velvet.g:1:186: FEATURE
                {
                mFEATURE(); 


                }
                break;
            case 18 :
                // Velvet.g:1:194: IMPORTINSTANCE
                {
                mIMPORTINSTANCE(); 


                }
                break;
            case 19 :
                // Velvet.g:1:209: IMPORTINTERFACE
                {
                mIMPORTINTERFACE(); 


                }
                break;
            case 20 :
                // Velvet.g:1:225: MANDATORY
                {
                mMANDATORY(); 


                }
                break;
            case 21 :
                // Velvet.g:1:235: MINUS
                {
                mMINUS(); 


                }
                break;
            case 22 :
                // Velvet.g:1:241: ONEOF
                {
                mONEOF(); 


                }
                break;
            case 23 :
                // Velvet.g:1:247: OP_AND
                {
                mOP_AND(); 


                }
                break;
            case 24 :
                // Velvet.g:1:254: OP_EQUIVALENT
                {
                mOP_EQUIVALENT(); 


                }
                break;
            case 25 :
                // Velvet.g:1:268: OP_IMPLIES
                {
                mOP_IMPLIES(); 


                }
                break;
            case 26 :
                // Velvet.g:1:279: OP_NOT
                {
                mOP_NOT(); 


                }
                break;
            case 27 :
                // Velvet.g:1:286: OP_OR
                {
                mOP_OR(); 


                }
                break;
            case 28 :
                // Velvet.g:1:292: OP_XOR
                {
                mOP_XOR(); 


                }
                break;
            case 29 :
                // Velvet.g:1:299: PLUS
                {
                mPLUS(); 


                }
                break;
            case 30 :
                // Velvet.g:1:304: SEMI
                {
                mSEMI(); 


                }
                break;
            case 31 :
                // Velvet.g:1:309: SOMEOF
                {
                mSOMEOF(); 


                }
                break;
            case 32 :
                // Velvet.g:1:316: START_C
                {
                mSTART_C(); 


                }
                break;
            case 33 :
                // Velvet.g:1:324: START_R
                {
                mSTART_R(); 


                }
                break;
            case 34 :
                // Velvet.g:1:332: USE
                {
                mUSE(); 


                }
                break;
            case 35 :
                // Velvet.g:1:336: VAR_BOOL
                {
                mVAR_BOOL(); 


                }
                break;
            case 36 :
                // Velvet.g:1:345: VAR_FLOAT
                {
                mVAR_FLOAT(); 


                }
                break;
            case 37 :
                // Velvet.g:1:355: VAR_INT
                {
                mVAR_INT(); 


                }
                break;
            case 38 :
                // Velvet.g:1:363: VAR_STRING
                {
                mVAR_STRING(); 


                }
                break;
            case 39 :
                // Velvet.g:1:374: BOOLEAN
                {
                mBOOLEAN(); 


                }
                break;
            case 40 :
                // Velvet.g:1:382: ID
                {
                mID(); 


                }
                break;
            case 41 :
                // Velvet.g:1:385: IDPath
                {
                mIDPath(); 


                }
                break;
            case 42 :
                // Velvet.g:1:392: INT
                {
                mINT(); 


                }
                break;
            case 43 :
                // Velvet.g:1:396: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 44 :
                // Velvet.g:1:402: STRING
                {
                mSTRING(); 


                }
                break;
            case 45 :
                // Velvet.g:1:409: WS
                {
                mWS(); 


                }
                break;
            case 46 :
                // Velvet.g:1:412: SL_COMMENT
                {
                mSL_COMMENT(); 


                }
                break;
            case 47 :
                // Velvet.g:1:423: ML_COMMENT
                {
                mML_COMMENT(); 


                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA11_eotS =
        "\5\uffff";
    static final String DFA11_eofS =
        "\5\uffff";
    static final String DFA11_minS =
        "\2\56\3\uffff";
    static final String DFA11_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA11_specialS =
        "\5\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "232:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA19_eotS =
        "\1\uffff\1\44\1\47\1\51\1\54\1\56\1\44\2\uffff\1\44\2\uffff\3\44"+
        "\1\70\1\44\2\uffff\1\44\2\uffff\1\44\2\uffff\4\44\1\100\4\uffff"+
        "\2\44\13\uffff\10\44\2\uffff\7\44\3\uffff\10\44\1\136\2\44\1\141"+
        "\2\44\1\144\14\44\1\uffff\2\44\1\uffff\2\44\1\uffff\1\165\1\166"+
        "\6\44\1\175\1\166\3\44\1\u0081\2\44\2\uffff\6\44\1\uffff\3\44\1"+
        "\uffff\1\u008d\1\u008e\2\44\1\u0091\2\44\1\u0094\3\44\2\uffff\1"+
        "\u0098\1\44\1\uffff\2\44\1\uffff\1\u009c\2\44\1\uffff\3\44\1\uffff"+
        "\1\u00a2\1\u00a3\1\u00a4\1\u00a5\1\44\4\uffff\1\u00a7\1\uffff";
    static final String DFA19_eofS =
        "\u00a8\uffff";
    static final String DFA19_minS =
        "\1\11\1\55\2\75\1\55\1\75\1\55\2\uffff\1\55\2\uffff\5\55\2\uffff"+
        "\1\55\2\uffff\1\55\2\uffff\4\55\1\56\3\uffff\1\52\2\55\13\uffff"+
        "\10\55\2\uffff\7\55\3\uffff\33\55\1\uffff\2\55\1\uffff\2\55\1\uffff"+
        "\20\55\2\uffff\6\55\1\uffff\3\55\1\uffff\13\55\2\uffff\2\55\1\uffff"+
        "\2\55\1\uffff\3\55\1\uffff\3\55\1\uffff\5\55\4\uffff\1\55\1\uffff";
    static final String DFA19_maxS =
        "\1\175\1\172\4\75\1\172\2\uffff\1\172\2\uffff\5\172\2\uffff\1\172"+
        "\2\uffff\1\172\2\uffff\4\172\1\145\3\uffff\1\57\2\172\13\uffff\10"+
        "\172\2\uffff\7\172\3\uffff\33\172\1\uffff\2\172\1\uffff\2\172\1"+
        "\uffff\20\172\2\uffff\6\172\1\uffff\3\172\1\uffff\13\172\2\uffff"+
        "\2\172\1\uffff\2\172\1\uffff\3\172\1\uffff\3\172\1\uffff\5\172\4"+
        "\uffff\1\172\1\uffff";
    static final String DFA19_acceptS =
        "\7\uffff\1\11\1\12\1\uffff\1\16\1\17\5\uffff\1\27\1\33\1\uffff\1"+
        "\35\1\36\1\uffff\1\40\1\41\5\uffff\1\53\1\54\1\55\3\uffff\1\50\1"+
        "\51\1\2\1\20\1\4\1\3\1\6\1\30\1\5\1\7\1\32\10\uffff\1\31\1\25\7"+
        "\uffff\1\52\1\56\1\57\33\uffff\1\45\2\uffff\1\34\2\uffff\1\42\20"+
        "\uffff\1\43\1\47\6\uffff\1\44\3\uffff\1\26\13\uffff\1\37\1\46\2"+
        "\uffff\1\13\2\uffff\1\21\3\uffff\1\1\3\uffff\1\22\5\uffff\1\23\1"+
        "\24\1\10\1\14\1\uffff\1\15";
    static final String DFA19_specialS =
        "\u00a8\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\40\2\uffff\1\40\22\uffff\1\40\1\5\1\37\3\uffff\1\21\1\uffff"+
            "\1\30\1\13\1\uffff\1\24\1\10\1\17\1\36\1\41\12\35\1\7\1\25\1"+
            "\4\1\2\1\3\2\uffff\32\34\4\uffff\1\34\1\uffff\1\1\1\32\1\6\1"+
            "\11\1\34\1\14\2\34\1\15\3\34\1\16\1\34\1\20\3\34\1\26\1\33\1"+
            "\31\2\34\1\23\2\34\1\27\1\22\1\12",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\43\1\42\30\43",
            "\1\46",
            "\1\50",
            "\1\53\17\uffff\1\52",
            "\1\55",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\10\43\1\57\5\43\1\60\13\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\61\25\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\64\3\43\1\62\6\43\1\63\16\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\65\14\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\66\31\43",
            "\1\43\1\45\1\uffff\12\43\4\uffff\1\67\2\uffff\32\43\4\uffff"+
            "\1\43\1\uffff\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\71\14\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\72\13\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\73\4\43\1\74\6\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\22\43\1\75\7\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\76\13\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\77\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\36\1\uffff\12\35\13\uffff\1\36\37\uffff\1\36",
            "",
            "",
            "",
            "\1\102\4\uffff\1\101",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\22\43\1\103\7\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\104\14\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\105\14\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\22\43\1\106\7\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\107\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\110\13\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\13\43\1\111\16\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\22\43\1\112\1\113\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\114\14\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\115\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\116\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\14\43\1\117\15\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\120\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\121\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\122\13\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\24\43\1\123\5\43",
            "",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\124\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\125\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\126\17\43\1\127\7\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\130\27\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\131\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\132\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\22\43\1\133\7\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\134\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\135\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\3\43\1\137\26\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\16\43\1\140\13\43\4\uffff"+
            "\1\43\1\uffff\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\142\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\10\43\1\143\21\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\13\43\1\145\16\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\146\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\147\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\150\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\151\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\152\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\153\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\24\43\1\154\5\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\155\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\156\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\157\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\160\10\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\161\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\5\43\1\162\24\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\16\43\1\163\13\43\4\uffff"+
            "\1\43\1\uffff\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\164\14\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\167\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\170\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\17\43\1\171\12\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\172\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\10\43\1\173\21\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\174\10\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\176\14\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\5\43\1\177\24\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\u0080\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\5\43\1\u0082\24\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\6\43\1\u0083\23\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\u0084\27\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\5\43\1\u0085\24\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\u0086\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\u0087\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\17\43\1\u0088\12\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\u0089\25\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\u008a\27\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\u008b\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\u008c\13\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\u008f\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\1\u0090\31\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\10\43\1\u0092\21\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\u0093\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\u0095\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\u0096\27\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\21\43\1\u0097\10\43",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\2\43\1\u0099\27\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\u009a\14\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\10\43\1\u009b\21\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\u009d\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\30\43\1\u009e\1\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\4\43\1\u009f\25\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\23\43\1\u00a0\6\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\16\43\1\u00a1\13\43",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\15\43\1\u00a6\14\43",
            "",
            "",
            "",
            "",
            "\1\43\1\45\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff"+
            "\32\43",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ABSTRACT | ATTR_OP_EQUALS | ATTR_OP_GREATER | ATTR_OP_GREATER_EQ | ATTR_OP_LESS | ATTR_OP_LESS_EQ | ATTR_OP_NOT_EQUALS | CINTERFACE | COLON | COMMA | CONCEPT | CONSTRAINT | DESCRIPTION | END_C | END_R | EQ | FEATURE | IMPORTINSTANCE | IMPORTINTERFACE | MANDATORY | MINUS | ONEOF | OP_AND | OP_EQUIVALENT | OP_IMPLIES | OP_NOT | OP_OR | OP_XOR | PLUS | SEMI | SOMEOF | START_C | START_R | USE | VAR_BOOL | VAR_FLOAT | VAR_INT | VAR_STRING | BOOLEAN | ID | IDPath | INT | FLOAT | STRING | WS | SL_COMMENT | ML_COMMENT );";
        }
    }
 

}