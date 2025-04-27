package br.com.cdb.bancoDigitalProjetoFinalCDB.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}

