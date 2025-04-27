package br.com.cdb.bancoDigitalProjetoFinalCDB.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String message)
    {
        super(message);
    }
}
