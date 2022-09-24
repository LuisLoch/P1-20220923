package br.unigran.p1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unigran.bancoDeDados.DBHelper;
import br.unigran.bancoDeDados.consumoDB;

public class MainActivity extends AppCompatActivity {
    TextView mediaConsumoTv;
    EditText kmAtual, qtdAbastecida, valor, data;
    List<Consumo> dados;
    ListView lista;
    DBHelper db;
    consumoDB consumoDB;
    Consumo consumo;
    //primeiro numero do array é a kmTotal, segundo dado é a quantidade abastecida total
    float dadosConsumo[] = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        kmAtual = findViewById(R.id.idKmAtual);
        qtdAbastecida = findViewById(R.id.idQtdAbastecida);
        valor = findViewById(R.id.idValor);
        data = findViewById(R.id.idData);
        mediaConsumoTv = findViewById(R.id.idMediaConsumo);
        lista = findViewById(R.id.idLista);

        dados = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        lista.setAdapter(adapter);

        consumoDB = new consumoDB(db);

        consumoDB.listarDados(dados, dadosConsumo);
        calculaMediaConsumo();

        acoes();
    }

    private void acoes(){
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                consumoDB.remover(dados.get(i).getId());
                consumoDB.listarDados(dados, dadosConsumo);
                calculaMediaConsumo();
                consumoDB.atualizar(lista);
                return false;
            }
        });
    }

    public void salvar(View view){
        if(consumo == null) consumo = new Consumo();

        try {
        consumo.setKmAtual(Integer.valueOf(kmAtual.getText().toString()));
        consumo.setQtdAbastecida(Integer.valueOf(qtdAbastecida.getText().toString()));
        consumo.setValor(Float.valueOf(valor.getText().toString()));
        consumo.setData(data.getText().toString());

        consumoDB.inserir(consumo);

        consumoDB.listarDados(dados, dadosConsumo);
        calculaMediaConsumo();
        consumoDB.atualizar(lista);

        consumo = null;

        Toast.makeText(getApplicationContext(), "Dados de consumo salvos", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Insira dados em todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void calculaMediaConsumo(){
        float mediaConsumo = 0;
        if(dadosConsumo[0]>0 && dadosConsumo[1]>0){
            mediaConsumo = dadosConsumo[0]/dadosConsumo[1];
            mediaConsumoTv.setText(mediaConsumo+"");
        }else
            mediaConsumoTv.setText("Ainda não há média de consumo.");
    }
}