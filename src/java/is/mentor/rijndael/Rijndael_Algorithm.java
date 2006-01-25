/**
 * 
 */
package is.mentor.rijndael;


/**
 * <p>
 * TODO tryggvil Describe Type Rijndael_Algorithm
 * </p>
 *  Last modified: $Date: 2006/01/25 01:37:09 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */

import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;

// Referenced classes of package Rijndael:
//            Rijndael_Properties

public final class Rijndael_Algorithm
{

    static void debug(String s)
    {
        System.out.println(">>> Rijndael_Algorithm: " + s);
    }

    static void trace(boolean flag, String s)
    {
        if(TRACE)
            System.out.println((flag ? "==> " : "<== ") + "Rijndael_Algorithm" + "." + s);
    }

    static void trace(String s)
    {
        if(TRACE)
            System.out.println("<=> Rijndael_Algorithm." + s);
    }

    static final int mul(int i, int j)
    {
        if(i != 0 && j != 0)
            return alog[(log[i & 255] + log[j & 255]) % 255];
        else
            return 0;
    }

    static final int mul4(int i, byte abyte0[])
    {
        if(i == 0)
        {
            return 0;
        } else
        {
            i = log[i & 255];
            int j = abyte0[0] == 0 ? 0 : alog[(i + log[abyte0[0] & 255]) % 255] & 255;
            int k = abyte0[1] == 0 ? 0 : alog[(i + log[abyte0[1] & 255]) % 255] & 255;
            int l = abyte0[2] == 0 ? 0 : alog[(i + log[abyte0[2] & 255]) % 255] & 255;
            int i1 = abyte0[3] == 0 ? 0 : alog[(i + log[abyte0[3] & 255]) % 255] & 255;
            return j << 24 | k << 16 | l << 8 | i1;
        }
    }

    public static Object makeKey(byte abyte0[])
        throws InvalidKeyException
    {
        return makeKey(abyte0, 16);
    }

    public static byte[] blockEncrypt(byte abyte0[], int i, Object obj)
    {
        int ai[][] = (int[][])((Object[])obj)[0];
        int j = ai.length - 1;
        int ai1[] = ai[0];
        int k = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[0];
        int l = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[1];
        int i1 = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[2];
        int j1 = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[3];
        for(int k2 = 1; k2 < j; k2++)
        {
            ai1 = ai[k2];
            int k1 = T1[k >>> 24 & 255] ^ T2[l >>> 16 & 255] ^ T3[i1 >>> 8 & 255] ^ T4[j1 & 255] ^ ai1[0];
            int l1 = T1[l >>> 24 & 255] ^ T2[i1 >>> 16 & 255] ^ T3[j1 >>> 8 & 255] ^ T4[k & 255] ^ ai1[1];
            int i2 = T1[i1 >>> 24 & 255] ^ T2[j1 >>> 16 & 255] ^ T3[k >>> 8 & 255] ^ T4[l & 255] ^ ai1[2];
            int j2 = T1[j1 >>> 24 & 255] ^ T2[k >>> 16 & 255] ^ T3[l >>> 8 & 255] ^ T4[i1 & 255] ^ ai1[3];
            k = k1;
            l = l1;
            i1 = i2;
            j1 = j2;
        }

        byte abyte1[] = new byte[16];
        ai1 = ai[j];
        int l2 = ai1[0];
        abyte1[0] = (byte)(S[k >>> 24 & 255] ^ l2 >>> 24);
        abyte1[1] = (byte)(S[l >>> 16 & 255] ^ l2 >>> 16);
        abyte1[2] = (byte)(S[i1 >>> 8 & 255] ^ l2 >>> 8);
        abyte1[3] = (byte)(S[j1 & 255] ^ l2);
        l2 = ai1[1];
        abyte1[4] = (byte)(S[l >>> 24 & 255] ^ l2 >>> 24);
        abyte1[5] = (byte)(S[i1 >>> 16 & 255] ^ l2 >>> 16);
        abyte1[6] = (byte)(S[j1 >>> 8 & 255] ^ l2 >>> 8);
        abyte1[7] = (byte)(S[k & 255] ^ l2);
        l2 = ai1[2];
        abyte1[8] = (byte)(S[i1 >>> 24 & 255] ^ l2 >>> 24);
        abyte1[9] = (byte)(S[j1 >>> 16 & 255] ^ l2 >>> 16);
        abyte1[10] = (byte)(S[k >>> 8 & 255] ^ l2 >>> 8);
        abyte1[11] = (byte)(S[l & 255] ^ l2);
        l2 = ai1[3];
        abyte1[12] = (byte)(S[j1 >>> 24 & 255] ^ l2 >>> 24);
        abyte1[13] = (byte)(S[k >>> 16 & 255] ^ l2 >>> 16);
        abyte1[14] = (byte)(S[l >>> 8 & 255] ^ l2 >>> 8);
        abyte1[15] = (byte)(S[i1 & 255] ^ l2);
        return abyte1;
    }

    public static byte[] blockDecrypt(byte abyte0[], int i, Object obj)
    {
        int ai[][] = (int[][])((Object[])obj)[1];
        int j = ai.length - 1;
        int ai1[] = ai[0];
        int k = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[0];
        int l = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[1];
        int i1 = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[2];
        int j1 = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai1[3];
        for(int k2 = 1; k2 < j; k2++)
        {
            ai1 = ai[k2];
            int k1 = T5[k >>> 24 & 255] ^ T6[j1 >>> 16 & 255] ^ T7[i1 >>> 8 & 255] ^ T8[l & 255] ^ ai1[0];
            int l1 = T5[l >>> 24 & 255] ^ T6[k >>> 16 & 255] ^ T7[j1 >>> 8 & 255] ^ T8[i1 & 255] ^ ai1[1];
            int i2 = T5[i1 >>> 24 & 255] ^ T6[l >>> 16 & 255] ^ T7[k >>> 8 & 255] ^ T8[j1 & 255] ^ ai1[2];
            int j2 = T5[j1 >>> 24 & 255] ^ T6[i1 >>> 16 & 255] ^ T7[l >>> 8 & 255] ^ T8[k & 255] ^ ai1[3];
            k = k1;
            l = l1;
            i1 = i2;
            j1 = j2;
        }

        byte abyte1[] = new byte[16];
        ai1 = ai[j];
        int l2 = ai1[0];
        abyte1[0] = (byte)(Si[k >>> 24 & 255] ^ l2 >>> 24);
        abyte1[1] = (byte)(Si[j1 >>> 16 & 255] ^ l2 >>> 16);
        abyte1[2] = (byte)(Si[i1 >>> 8 & 255] ^ l2 >>> 8);
        abyte1[3] = (byte)(Si[l & 255] ^ l2);
        l2 = ai1[1];
        abyte1[4] = (byte)(Si[l >>> 24 & 255] ^ l2 >>> 24);
        abyte1[5] = (byte)(Si[k >>> 16 & 255] ^ l2 >>> 16);
        abyte1[6] = (byte)(Si[j1 >>> 8 & 255] ^ l2 >>> 8);
        abyte1[7] = (byte)(Si[i1 & 255] ^ l2);
        l2 = ai1[2];
        abyte1[8] = (byte)(Si[i1 >>> 24 & 255] ^ l2 >>> 24);
        abyte1[9] = (byte)(Si[l >>> 16 & 255] ^ l2 >>> 16);
        abyte1[10] = (byte)(Si[k >>> 8 & 255] ^ l2 >>> 8);
        abyte1[11] = (byte)(Si[j1 & 255] ^ l2);
        l2 = ai1[3];
        abyte1[12] = (byte)(Si[j1 >>> 24 & 255] ^ l2 >>> 24);
        abyte1[13] = (byte)(Si[i1 >>> 16 & 255] ^ l2 >>> 16);
        abyte1[14] = (byte)(Si[l >>> 8 & 255] ^ l2 >>> 8);
        abyte1[15] = (byte)(Si[k & 255] ^ l2);
        return abyte1;
    }

    public static boolean self_test()
    {
        return self_test(16);
    }

    public static int blockSize()
    {
        return 16;
    }

    public static synchronized Object makeKey(byte abyte0[], int i)
        throws InvalidKeyException
    {
        if(abyte0 == null)
            throw new InvalidKeyException("Empty key");
        if(abyte0.length != 16 && abyte0.length != 24 && abyte0.length != 32)
            throw new InvalidKeyException("Incorrect key length");
        int j = getRounds(abyte0.length, i);
        int k = i / 4;
        int ai[][] = new int[j + 1][k];
        int ai1[][] = new int[j + 1][k];
        int l = (j + 1) * k;
        int i1 = abyte0.length / 4;
        int ai2[] = new int[i1];
        int j1 = 0;
        int j2 = 0;
        while(j1 < i1) 
            ai2[j1++] = (abyte0[j2++] & 255) << 24 | (abyte0[j2++] & 255) << 16 | (abyte0[j2++] & 255) << 8 | abyte0[j2++] & 255;
        int l3 = 0;
        for(int k2 = 0; k2 < i1 && l3 < l; l3++)
        {
            ai[l3 / k][l3 % k] = ai2[k2];
            ai1[j - l3 / k][l3 % k] = ai2[k2];
            k2++;
        }

        int l4 = 0;
        while(l3 < l) 
        {
            int i4 = ai2[i1 - 1];
            ai2[0] ^= (S[i4 >>> 16 & 255] & 255) << 24 ^ (S[i4 >>> 8 & 255] & 255) << 16 ^ (S[i4 & 255] & 255) << 8 ^ S[i4 >>> 24 & 255] & 255 ^ (rcon[l4++] & 255) << 24;
            if(i1 != 8)
            {
                int k1 = 1;
                int l2 = 0;
                while(k1 < i1) 
                    ai2[k1++] ^= ai2[l2++];
            } else
            {
                int l1 = 1;
                int i3 = 0;
                while(l1 < i1 / 2) 
                    ai2[l1++] ^= ai2[i3++];
                int j4 = ai2[i1 / 2 - 1];
                ai2[i1 / 2] ^= S[j4 & 255] & 255 ^ (S[j4 >>> 8 & 255] & 255) << 8 ^ (S[j4 >>> 16 & 255] & 255) << 16 ^ (S[j4 >>> 24 & 255] & 255) << 24;
                i3 = i1 / 2;
                for(int i2 = i3 + 1; i2 < i1;)
                    ai2[i2++] ^= ai2[i3++];

            }
            for(int j3 = 0; j3 < i1 && l3 < l; l3++)
            {
                ai[l3 / k][l3 % k] = ai2[j3];
                ai1[j - l3 / k][l3 % k] = ai2[j3];
                j3++;
            }

        }
        for(int i5 = 1; i5 < j; i5++)
        {
            for(int k3 = 0; k3 < k; k3++)
            {
                int k4 = ai1[i5][k3];
                ai1[i5][k3] = U1[k4 >>> 24 & 255] ^ U2[k4 >>> 16 & 255] ^ U3[k4 >>> 8 & 255] ^ U4[k4 & 255];
            }

        }

        Object aobj[] = {
            ai, ai1
        };
        return ((Object) (aobj));
    }

    public static byte[] blockEncrypt(byte abyte0[], int i, Object obj, int j)
    {
        if(j == 16)
            return blockEncrypt(abyte0, i, obj);
        Object aobj[] = (Object[])obj;
        int ai[][] = (int[][])aobj[0];
        int k = j / 4;
        int l = ai.length - 1;
        byte byte0 = k != 4 ? ((byte)(k != 6 ? 2 : 1)) : 0;
        int i1 = shifts[byte0][1][0];
        int j1 = shifts[byte0][2][0];
        int k1 = shifts[byte0][3][0];
        int ai1[] = new int[k];
        int ai2[] = new int[k];
        byte abyte1[] = new byte[j];
        int k2 = 0;
        for(int l1 = 0; l1 < k; l1++)
            ai2[l1] = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai[0][l1];

        for(int i3 = 1; i3 < l; i3++)
        {
            for(int i2 = 0; i2 < k; i2++)
                ai1[i2] = T1[ai2[i2] >>> 24 & 255] ^ T2[ai2[(i2 + i1) % k] >>> 16 & 255] ^ T3[ai2[(i2 + j1) % k] >>> 8 & 255] ^ T4[ai2[(i2 + k1) % k] & 255] ^ ai[i3][i2];

            System.arraycopy(ai1, 0, ai2, 0, k);
        }

        for(int j2 = 0; j2 < k; j2++)
        {
            int l2 = ai[l][j2];
            abyte1[k2++] = (byte)(S[ai2[j2] >>> 24 & 255] ^ l2 >>> 24);
            abyte1[k2++] = (byte)(S[ai2[(j2 + i1) % k] >>> 16 & 255] ^ l2 >>> 16);
            abyte1[k2++] = (byte)(S[ai2[(j2 + j1) % k] >>> 8 & 255] ^ l2 >>> 8);
            abyte1[k2++] = (byte)(S[ai2[(j2 + k1) % k] & 255] ^ l2);
        }

        return abyte1;
    }

    public static byte[] blockDecrypt(byte abyte0[], int i, Object obj, int j)
    {
        if(j == 16)
            return blockDecrypt(abyte0, i, obj);
        Object aobj[] = (Object[])obj;
        int ai[][] = (int[][])aobj[1];
        int k = j / 4;
        int l = ai.length - 1;
        byte byte0 = k != 4 ? ((byte)(k != 6 ? 2 : 1)) : 0;
        int i1 = shifts[byte0][1][1];
        int j1 = shifts[byte0][2][1];
        int k1 = shifts[byte0][3][1];
        int ai1[] = new int[k];
        int ai2[] = new int[k];
        byte abyte1[] = new byte[j];
        int k2 = 0;
        for(int l1 = 0; l1 < k; l1++)
            ai2[l1] = ((abyte0[i++] & 255) << 24 | (abyte0[i++] & 255) << 16 | (abyte0[i++] & 255) << 8 | abyte0[i++] & 255) ^ ai[0][l1];

        for(int i3 = 1; i3 < l; i3++)
        {
            for(int i2 = 0; i2 < k; i2++)
                ai1[i2] = T5[ai2[i2] >>> 24 & 255] ^ T6[ai2[(i2 + i1) % k] >>> 16 & 255] ^ T7[ai2[(i2 + j1) % k] >>> 8 & 255] ^ T8[ai2[(i2 + k1) % k] & 255] ^ ai[i3][i2];

            System.arraycopy(ai1, 0, ai2, 0, k);
        }

        for(int j2 = 0; j2 < k; j2++)
        {
            int l2 = ai[l][j2];
            abyte1[k2++] = (byte)(Si[ai2[j2] >>> 24 & 255] ^ l2 >>> 24);
            abyte1[k2++] = (byte)(Si[ai2[(j2 + i1) % k] >>> 16 & 255] ^ l2 >>> 16);
            abyte1[k2++] = (byte)(Si[ai2[(j2 + j1) % k] >>> 8 & 255] ^ l2 >>> 8);
            abyte1[k2++] = (byte)(Si[ai2[(j2 + k1) % k] & 255] ^ l2);
        }

        return abyte1;
    }

    private static boolean self_test(int i)
    {
        boolean flag = false;
        try
        {
            byte abyte0[] = new byte[i];
            byte abyte1[] = new byte[16];
            for(int j = 0; j < i; j++)
                abyte0[j] = (byte)j;

            for(int k = 0; k < 16; k++)
                abyte1[k] = (byte)k;

            Object obj = makeKey(abyte0, 16);
            byte abyte2[] = blockEncrypt(abyte1, 0, obj, 16);
            byte abyte3[] = blockDecrypt(abyte2, 0, obj, 16);
            flag = areEqual(abyte1, abyte3);
            if(!flag)
                throw new RuntimeException("Symmetric operation failed");
        }
        catch(Exception exception) { }
        return flag;
    }

    public static int getRounds(int i, int j)
    {
        switch(i)
        {
        case 16: // '\020'
            if(j == 16)
                return 10;
            return j != 24 ? 14 : 12;

        case 24: // '\030'
            return j == 32 ? 14 : 12;
        }
        return 14;
    }

    private static boolean areEqual(byte abyte0[], byte abyte1[])
    {
        int i = abyte0.length;
        if(i != abyte1.length)
            return false;
        for(int j = 0; j < i; j++)
            if(abyte0[j] != abyte1[j])
                return false;

        return true;
    }

    private static String byteToString(int i)
    {
        char ac[] = {
            HEX_DIGITS[i >>> 4 & 15], HEX_DIGITS[i & 15]
        };
        return new String(ac);
    }

    private static String intToString(int i)
    {
        char ac[] = new char[8];
        for(int j = 7; j >= 0; j--)
        {
            ac[j] = HEX_DIGITS[i & 15];
            i >>>= 4;
        }

        return new String(ac);
    }

    private static String toString(byte abyte0[])
    {
        int i = abyte0.length;
        char ac[] = new char[i * 2];
        int j = 0;
        int k = 0;
        while(j < i) 
        {
            byte byte0 = abyte0[j++];
            ac[k++] = HEX_DIGITS[byte0 >>> 4 & 15];
            ac[k++] = HEX_DIGITS[byte0 & 15];
        }
        return new String(ac);
    }

    private static String toString(int ai[])
    {
        int i = ai.length;
        char ac[] = new char[i * 8];
        int j = 0;
        int k = 0;
        for(; j < i; j++)
        {
            int l = ai[j];
            ac[k++] = HEX_DIGITS[l >>> 28 & 15];
            ac[k++] = HEX_DIGITS[l >>> 24 & 15];
            ac[k++] = HEX_DIGITS[l >>> 20 & 15];
            ac[k++] = HEX_DIGITS[l >>> 16 & 15];
            ac[k++] = HEX_DIGITS[l >>> 12 & 15];
            ac[k++] = HEX_DIGITS[l >>> 8 & 15];
            ac[k++] = HEX_DIGITS[l >>> 4 & 15];
            ac[k++] = HEX_DIGITS[l & 15];
        }

        return new String(ac);
    }

    public static void main(String args[])
    {
        self_test(16);
        self_test(24);
        self_test(32);
    }

    public Rijndael_Algorithm()
    {
    }

    static final String NAME = "Rijndael_Algorithm";
    static final boolean IN = true;
    static final boolean OUT = false;
    static final boolean DEBUG = false;
    static final int debuglevel = 0;
    static final PrintWriter err = null;
    static final boolean TRACE = true;//Rijndael_Properties.isTraceable("Rijndael_Algorithm");
    static final int BLOCK_SIZE = 16;
    static final int alog[];
    static final int log[];
    static final byte S[];
    static final byte Si[];
    static final int T1[];
    static final int T2[];
    static final int T3[];
    static final int T4[];
    static final int T5[];
    static final int T6[];
    static final int T7[];
    static final int T8[];
    static final int U1[];
    static final int U2[];
    static final int U3[];
    static final int U4[];
    static final byte rcon[];
    static final int shifts[][][] = {
        {
            new int[2], {
                1, 3
            }, {
                2, 2
            }, {
                3, 1
            }
        }, {
            new int[2], {
                1, 5
            }, {
                2, 4
            }, {
                3, 3
            }
        }, {
            new int[2], {
                1, 7
            }, {
                3, 5
            }, {
                4, 4
            }
        }
    };
    private static final char HEX_DIGITS[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };

    static 
    {
        alog = new int[256];
        log = new int[256];
        S = new byte[256];
        Si = new byte[256];
        T1 = new int[256];
        T2 = new int[256];
        T3 = new int[256];
        T4 = new int[256];
        T5 = new int[256];
        T6 = new int[256];
        T7 = new int[256];
        T8 = new int[256];
        U1 = new int[256];
        U2 = new int[256];
        U3 = new int[256];
        U4 = new int[256];
        rcon = new byte[30];
        long l = System.currentTimeMillis();
        boolean flag = false;
        alog[0] = 1;
        for(int i = 1; i < 256; i++)
        {
            int j2 = alog[i - 1] << 1 ^ alog[i - 1];
            if((j2 & 256) != 0)
                j2 ^= 283;
            alog[i] = j2;
        }

        for(int j = 1; j < 255; j++)
            log[alog[j]] = j;

        byte abyte0[][] = {
            {
                1, 1, 1, 1, 1, 0, 0, 0
            }, {
                0, 1, 1, 1, 1, 1, 0, 0
            }, {
                0, 0, 1, 1, 1, 1, 1, 0
            }, {
                0, 0, 0, 1, 1, 1, 1, 1
            }, {
                1, 0, 0, 0, 1, 1, 1, 1
            }, {
                1, 1, 0, 0, 0, 1, 1, 1
            }, {
                1, 1, 1, 0, 0, 0, 1, 1
            }, {
                1, 1, 1, 1, 0, 0, 0, 1
            }
        };
        byte abyte1[] = {
            0, 1, 1, 0, 0, 0, 1, 1
        };
        byte abyte2[][] = new byte[256][8];
        abyte2[1][7] = 1;
        for(int k = 2; k < 256; k++)
        {
            int k2 = alog[255 - log[k]];
            for(int j4 = 0; j4 < 8; j4++)
                abyte2[k][j4] = (byte)(k2 >>> 7 - j4 & 1);

        }

        byte abyte3[][] = new byte[256][8];
        for(int i1 = 0; i1 < 256; i1++)
        {
            for(int k4 = 0; k4 < 8; k4++)
            {
                abyte3[i1][k4] = abyte1[k4];
                for(int l2 = 0; l2 < 8; l2++)
                    abyte3[i1][k4] ^= abyte0[k4][l2] * abyte2[i1][l2];

            }

        }

        for(int j1 = 0; j1 < 256; j1++)
        {
            S[j1] = (byte)(abyte3[j1][0] << 7);
            for(int l4 = 1; l4 < 8; l4++)
                S[j1] ^= abyte3[j1][l4] << 7 - l4;

            Si[S[j1] & 255] = (byte)j1;
        }

        byte abyte4[][] = {
            {
                2, 1, 1, 3
            }, {
                3, 2, 1, 1
            }, {
                1, 3, 2, 1
            }, {
                1, 1, 3, 2
            }
        };
        byte abyte5[][] = new byte[4][8];
        for(int k1 = 0; k1 < 4; k1++)
        {
            for(int i3 = 0; i3 < 4; i3++)
                abyte5[k1][i3] = abyte4[k1][i3];

            abyte5[k1][k1 + 4] = 1;
        }

        byte abyte6[][] = new byte[4][4];
        for(int l1 = 0; l1 < 4; l1++)
        {
            byte byte0 = abyte5[l1][l1];
            if(byte0 == 0)
            {
                int i5;
                for(i5 = l1 + 1; abyte5[i5][l1] == 0 && i5 < 4; i5++);
                if(i5 == 4)
                    throw new RuntimeException("G matrix is not invertible");
                for(int j3 = 0; j3 < 8; j3++)
                {
                    byte byte1 = abyte5[l1][j3];
                    abyte5[l1][j3] = abyte5[i5][j3];
                    abyte5[i5][j3] = byte1;
                }

                byte0 = abyte5[l1][l1];
            }
            for(int k3 = 0; k3 < 8; k3++)
                if(abyte5[l1][k3] != 0)
                    abyte5[l1][k3] = (byte)alog[((255 + log[abyte5[l1][k3] & 255]) - log[byte0 & 255]) % 255];

            for(int j5 = 0; j5 < 4; j5++)
                if(l1 != j5)
                {
                    for(int l3 = l1 + 1; l3 < 8; l3++)
                        abyte5[j5][l3] ^= mul(abyte5[l1][l3], abyte5[j5][l1]);

                    abyte5[j5][l1] = 0;
                }

        }

        for(int i2 = 0; i2 < 4; i2++)
        {
            for(int i4 = 0; i4 < 4; i4++)
                abyte6[i2][i4] = abyte5[i2][i4 + 4];

        }

        for(int k5 = 0; k5 < 256; k5++)
        {
            byte byte2 = S[k5];
            T1[k5] = mul4(byte2, abyte4[0]);
            T2[k5] = mul4(byte2, abyte4[1]);
            T3[k5] = mul4(byte2, abyte4[2]);
            T4[k5] = mul4(byte2, abyte4[3]);
            byte2 = Si[k5];
            T5[k5] = mul4(byte2, abyte6[0]);
            T6[k5] = mul4(byte2, abyte6[1]);
            T7[k5] = mul4(byte2, abyte6[2]);
            T8[k5] = mul4(byte2, abyte6[3]);
            U1[k5] = mul4(k5, abyte6[0]);
            U2[k5] = mul4(k5, abyte6[1]);
            U3[k5] = mul4(k5, abyte6[2]);
            U4[k5] = mul4(k5, abyte6[3]);
        }

        rcon[0] = 1;
        int i6 = 1;
        for(int l5 = 1; l5 < 30;)
            rcon[l5++] = (byte)(i6 = mul(2, i6));

        l = System.currentTimeMillis() - l;
    }
}
