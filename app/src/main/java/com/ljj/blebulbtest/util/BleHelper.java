package com.ljj.blebulbtest.util;

public class BleHelper {
    public int PUBLIC_KEYS_G = 5;
    public long random = 0;
    public byte[] cryptKey;
    public byte[] data;
    long p = 4294967237L;
    public long DHKeyGenerateDefault(long random) {
        long test = powmodp(PUBLIC_KEYS_G, random, p);
        return test;
    }

    public long DHKeyGenerateExchanges(long random, long exchangeKey) {
        long tss = DHKGE(random, exchangeKey);
        return tss;
    }


    public long DHKGE(long random, long exchangeKey) {

        return powmodp(exchangeKey, random, p);
    }

    private long powmodp(long a, long b, long publishKey_P) {
        if (a > publishKey_P) {
            a %= publishKey_P;
        }
        return pow_mod_p(a, b, publishKey_P);
    }

    private long pow_mod_p(long a, long b, long publishKey_p) {
        if (1 == b) {
            return a;
        }
        long t = pow_mod_p(a, b >> 1, publishKey_p);
        t = mul_mod_p(t, t, publishKey_p);
        if ((b % 2) != 0) {
            t = mul_mod_p(t, a, publishKey_p);
        }
        return t;
    }

    long mul_mod_p(long a, long b, long publishKey_P) {
        long m = 0;
        while (b != 0) {
            if (b % 2 == 1) {
                long t = publishKey_P - a;
                if (m >= t) {
                    m -= t;
                } else {
                    m += a;
                }
            }
            if (a >= publishKey_P - a) {
                a = a * 2 - publishKey_P;
            } else {
                a = a * 2;
            }
            b >>= 1;
        }
        return m;
    }
}