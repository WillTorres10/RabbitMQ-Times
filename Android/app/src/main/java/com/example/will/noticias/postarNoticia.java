package com.example.will.noticias;

import java.lang.Thread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.will.noticias.enviar;

public class postarNoticia extends AppCompatActivity {

    TextView ipHost, titulo, conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postar_noticia);
        this.ipHost = findViewById(R.id.ip);
        this.titulo = findViewById(R.id.titulo);
        this.conteudo = findViewById(R.id.conteudo);
    }

    public void publicar(View v){
        String Host = this.ipHost.getText().toString();
        String Titulo = this.titulo.getText().toString();
        String Conteudo = this.conteudo.getText().toString();
        int Teor = this.teor();
        enviar env = null;
        if(Teor == 3){
            env = new enviar(Titulo,Conteudo,Host,"ambos");
        }else if(Teor == 2){
            env = new enviar(Titulo,Conteudo,Host,"corinthans");
        }else if(Teor == 1){
            env = new enviar(Titulo,Conteudo,Host,"palmeiras");
        }
        System.out.println(env.Rota);
        Thread th = env;
        th.start();
    }

    private int teor(){
        int corinthans = 0, palmeiras = 0;
        if(titulo.getText().toString().contains("corinthans")){
            corinthans = 1;
        }
        if(titulo.getText().toString().contains("palmeiras")){
            palmeiras = 1;
        }
        if(conteudo.getText().toString().contains("corinthans")){
            corinthans = 1;
        }
        if(conteudo.getText().toString().contains("palmeiras")){
            palmeiras = 1;
        }
        if(corinthans == 1 && palmeiras == 1){
            return 3;
        }else if(corinthans == 1 && palmeiras == 0){
            return 2;
        }else if(corinthans == 0 && palmeiras == 1){
            return 1;
        }else if(corinthans == 0 && palmeiras == 0){
            return 0;
        }
        return 0;
    }
}
