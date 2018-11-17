package com.example.root.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.myapplication.dao.UsuarioDAO;
import com.example.root.myapplication.model.Usuario;
import com.example.root.myapplication.util.Mensagem;


public class signup extends AppCompatActivity {

    private EditText edtNome, edtLogin, edtSenha;
    private TextView sup,sin;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private int idusuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usuarioDAO = new UsuarioDAO(this);

        edtNome  = (EditText) findViewById(R.id.edtNome);
        edtLogin = (EditText) findViewById(R.id.usrusr);
        edtSenha = (EditText) findViewById(R.id.pswd);
        sup = (TextView)findViewById(R.id.sup);
        sin = (TextView)findViewById(R.id.sin);

        sin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(signup.this, login.class);
                startActivity(it);
            }
        });

        /*//Modo de edição
        idusuario = getIntent().getIntExtra("USUARIO_ID", 0);
        if(idusuario > 0){
            Usuario model = usuarioDAO.buscarUsuarioPorId(idusuario);
            edtNome.setText(model.getNome());
            edtLogin.setText(model.getLogin());
            edtSenha.setText(model.getSenha());
            setTitle("Atualizar usuário");
        }*/
    }

    @Override
    protected void onDestroy() {
        usuarioDAO.fechar();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastros, menu);

        return true;
    }


    public void cadastrar(View view) {

        boolean validacao = true;

        String nome = edtNome.getText().toString();
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        if (nome == null || nome.equals("")) {
            validacao = false;
            edtNome.setError(getString(R.string.campo_obrigatorio));
        }

        if (login == null || login.equals("")) {
            validacao = false;
            edtLogin.setError(getString(R.string.campo_obrigatorio));
        }

        if (senha == null || senha.equals("")) {
            validacao = false;
            edtSenha.setError(getString(R.string.campo_obrigatorio));
        }

        if (validacao) {
            usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setSenha(senha);

            usuarioDAO.salvarUsuario(usuario);

            Mensagem.Msg(this, getString(R.string.mensagem_cadastro));



            Intent it = new Intent(signup.this, login.class);
            startActivity(it);


            }



    }
}
