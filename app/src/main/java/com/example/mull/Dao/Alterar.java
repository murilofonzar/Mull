package com.example.mull.Dao;

public class Alterar {

    String Endereco;
    String Complemento;
    String CEP;
    String UF;
    String Numero;

    public Alterar() {
    }

    public Alterar(String Endereco, String Complemento,String CEP, String UF, String Numero) {
        this.Endereco = Endereco;
        this.Complemento = Complemento;
        this.CEP = CEP;
        this.UF = UF;
        this.Numero = Numero;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
