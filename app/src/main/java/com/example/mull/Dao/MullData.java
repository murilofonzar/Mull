package com.example.mull.Dao;

public class MullData {
    String Nome, username, CEP, CPF;

    public MullData() {

    }

    public MullData(String Nome, String username, String CEP, String CPF) {

        this.Nome = Nome;
        this.username = username;
        this.CEP = CEP;
        this.CPF = CPF;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}
