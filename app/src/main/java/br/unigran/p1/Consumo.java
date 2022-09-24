package br.unigran.p1;

import java.util.Date;

public class Consumo {
    private Integer id, kmAtual, qtdAbastecida;
    private Float valor;
    private String data;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getKmAtual() { return kmAtual; }

    public void setKmAtual(Integer kmAtual) { this.kmAtual = kmAtual; }

    public Integer getQtdAbastecida() { return qtdAbastecida; }

    public void setQtdAbastecida(Integer qtdAbastecida) { this.qtdAbastecida = qtdAbastecida; }

    public Float getValor() { return valor; }

    public void setValor(Float valor) { this.valor = valor; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    @Override
    public String toString() { return kmAtual + " " + qtdAbastecida + " " + valor + " " + data + ""; }
}
