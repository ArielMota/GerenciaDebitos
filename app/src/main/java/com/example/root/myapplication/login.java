package com.example.root.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.root.myapplication.dao.UsuarioDAO;
import com.example.root.myapplication.util.Mensagem;

public class login extends AppCompatActivity
{
    private TextView sup,sin;

    private EditText edtUsuario, edtSenha;
    private UsuarioDAO helper;
    private CheckBox ckbConectado;

    private static final String MANTER_CONECTADO = "manter_conectado";
    private static final String PREFERENCE_NAME  = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sup = (TextView)findViewById(R.id.sup);
        sin = (TextView)findViewById(R.id.sin);
        //fbook = (TextView)findViewById(R.id.fboook);
        //acc = (TextView)findViewById(R.id.act);
        //mal = (EditText)findViewById(R.id.mal);
        edtUsuario = (EditText)findViewById(R.id.usrusr);
        edtSenha = (EditText)findViewById(R.id.pswd);
        ckbConectado = (CheckBox) findViewById(R.id.login_ckbConectado);

        helper = new UsuarioDAO(this);

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado             = preferences.getBoolean(MANTER_CONECTADO, false);

        if(conectado){
            ChamarMainAcitivity(String.valueOf(edtUsuario.getText()));
        }

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(login.this, signup.class);
                startActivity(it);
            }
        });

    }

    public void logar(View view){
        String usuario = edtUsuario.getText().toString();
        String senha   = edtSenha.getText().toString();

        boolean validacao = true;

        if(usuario == null || usuario.equals("")){
            validacao = false;
            edtUsuario.setError(getString(R.string.login_valUsuario));
        }

        if(senha == null || senha.equals("")){
            validacao = false;
            edtSenha.setError(getString(R.string.login_valSenha));
        }

        if(validacao){
            //logar
            if(helper.logar(usuario,senha)){
                if(ckbConectado.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor     = sharedPreferences.edit();

                    editor.putBoolean(MANTER_CONECTADO, true);
                    editor.commit();
                }

                ChamarMainAcitivity(usuario);
            }else{
                Mensagem.Msg(this, getString(R.string.msg_login_incorreto));
            }
        }
    }

    private void ChamarMainAcitivity(String nome){
        //startActivity(new Intent(this, MainActivity.class));
        // finish();
        Bundle bundle = new Bundle();
        Intent i = new Intent(this,MainActivity.class);

        bundle.putString("nome", String.valueOf(nome));

        i.putExtras(bundle);

        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}