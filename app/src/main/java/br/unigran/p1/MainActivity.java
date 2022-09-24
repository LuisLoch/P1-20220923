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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unigran.bancoDeDados.DBHelper;
import br.unigran.bancoDeDados.consumoDB;

public class MainActivity extends AppCompatActivity {
    EditText kmAtual, qtdAbastecida, valor, data;
    List<Consumo> dados;
    ListView lista;
    DBHelper db;
    consumoDB consumoDB;
    Consumo consumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        kmAtual = findViewById(R.id.idKmAtual);
        qtdAbastecida = findViewById(R.id.idQtdAbastecida);
        valor = findViewById(R.id.idValor);
        data = findViewById(R.id.idData);
        lista = findViewById(R.id.idLista);

        dados = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        lista.setAdapter(adapter);

        consumoDB = new consumoDB(db);

        consumoDB.listarDados(dados);

        acoes();
    }

    private void acoes(){
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Deseja realmente remover o dado de consumo?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                consumoDB.remover(dados.get(i).getId());
                                consumoDB.listarDados(dados);
                                consumoDB.atualizar(lista);
                            }
                        })
                        .setNegativeButton("Não", null)
                        .create().show();
                return false;
            }
        });
    }

    public void salvar(View view){
        if(consumo == null) consumo = new Consumo();

        consumo.setKmAtual(Integer.valueOf(kmAtual.getText().toString()));
        consumo.setQtdAbastecida(Integer.valueOf(qtdAbastecida.getText().toString()));
        consumo.setValor(Float.valueOf(valor.getText().toString()));
        consumo.setData(data.getText().toString());

        consumoDB.inserir(consumo);

        consumoDB.listarDados(dados);

        consumoDB.atualizar(lista);

        consumo = null;

        Toast.makeText(getApplicationContext(), "Dados de consumo salvos", Toast.LENGTH_SHORT).show();
    }
}