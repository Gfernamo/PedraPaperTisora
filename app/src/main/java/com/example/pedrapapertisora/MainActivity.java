package com.example.pedrapapertisora;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton info,pedra,paper,tisores;
    TextView guanyadesJugador,guanyadesCPU,guanyador;
    ImageView selectJugador,selectCPU;
    Button unAltre;
    int opcioCPU,opcioJugador; // 0 sera pedra, 1 sera paper, 2 sera tisores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),info.class);
                startActivity(intent);
            }
        });
        pedra = findViewById(R.id.pedra);
        pedra.setOnClickListener(this);
        paper = findViewById(R.id.paper);
        paper.setOnClickListener(this);
        tisores = findViewById(R.id.tisores);
        tisores.setOnClickListener(this);
        guanyadesJugador = findViewById(R.id.guanyadesJugador);
        guanyadesCPU = findViewById(R.id.guanyadesCPU);
        guanyador = findViewById(R.id.guanyador);
        guanyador.setVisibility(View.GONE);
        selectJugador = findViewById(R.id.selectJugador);
        selectJugador.setVisibility(View.GONE);
        selectCPU = findViewById(R.id.selectCPU);
        selectCPU.setVisibility(View.GONE);
        unAltre = findViewById(R.id.replay);
        unAltre.setVisibility(View.GONE);
        unAltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarJoc();
            }
        });
    }

    private void reiniciarJoc() {
        pedra.setClickable(true);
        paper.setClickable(true);
        tisores.setClickable(true);
        selectJugador.setVisibility(View.GONE);
        selectCPU.setVisibility(View.GONE);
        unAltre.setVisibility(View.GONE);
        guanyador.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int puntsJugador,puntsCPU;
        String resultat;

        if (pedra.getId()==view.getId())
        {
            triaPedra();
            triaCPU();
        }
        else if(paper.getId()==view.getId())
        {
            triaPaper();
            triaCPU();

        }
        else if (tisores.getId()==view.getId())
        {
            triaTisores();
            triaCPU();
        }
        desactivarImatges();
        selectJugador.setVisibility(View.VISIBLE);
        selectCPU.setVisibility(View.VISIBLE);
        /// A continuació verificio les condicions de victoria del jugador
        if (opcioJugador==0&&opcioCPU==2||opcioJugador==1&&opcioCPU==0||opcioJugador==2&&opcioCPU==1)
        {
            guanyador.setText("Guanya el Jugador!");
            resultat = "Has guanyat!";
            puntsJugador=Integer.parseInt(guanyadesJugador.getText().toString());
            puntsJugador++;
            guanyadesJugador.setText(Integer.toString(puntsJugador));
        }
        /// Si no es cap d'aquestes, verifico si es un empat
        else if (opcioCPU==opcioJugador)
        {
            guanyador.setText("Heu empatat! Ningú guanya punts");
            resultat = "Has empatat!";

        }
        /// Si no guanya el jugador, i tampoc es un empat, sols pot ser la victoria de la CPU
        else
        {
            guanyador.setText("Guanya la CPU!");
            resultat = "Has perdut!";
            puntsCPU=Integer.parseInt(guanyadesCPU.getText().toString());
            puntsCPU++;
            guanyadesCPU.setText(Integer.toString(puntsCPU));
        }
        guanyador.setVisibility(View.VISIBLE);
        unAltre.setVisibility(View.VISIBLE);
        mostrarFinal(resultat);
    }
    public void mostrarFinal(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(text);
        builder.setMessage("Vols tornar a jugar?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reiniciarJoc();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void desactivarImatges() {
        pedra.setClickable(false);
        paper.setClickable(false);
        tisores.setClickable(false);
    }

    private void triaPedra() {
        opcioJugador=0;
        selectJugador.setImageResource(R.drawable.piedragrande);
    }
    private void triaPaper() {
        opcioJugador=1;
        selectJugador.setImageResource(R.drawable.papelgrande);
    }
    private void triaTisores() {
        opcioJugador=2;
        selectJugador.setImageResource(R.drawable.tijeragrande);
    }

    private void triaCPU() {
        Random r = new Random();
        opcioCPU=r.nextInt(3);
        if (opcioCPU==0) // Aqui tria Pedra
        {
            selectCPU.setImageResource(R.drawable.piedragrande);
        }
        else if (opcioCPU==1) // Aqui tria Paper
        {
            selectCPU.setImageResource(R.drawable.papelgrande);
        }
        else // Aqui tria Tisores
        {
            selectCPU.setImageResource(R.drawable.tijeragrande);
        }
    }
}