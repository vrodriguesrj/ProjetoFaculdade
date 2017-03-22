package funcaoibge.cursoandroid.com.projetofaculdade;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import funcaoibge.cursoandroid.com.projetofaculdade.adapter.AdapterContato;
import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;
import funcaoibge.cursoandroid.com.projetofaculdade.eventos.RecebeContatoAdapter;
import funcaoibge.cursoandroid.com.projetofaculdade.dao.ContatoDAO;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterContato adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contato> mListaContatos = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configurarAdapter() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ContatoDAO dao = new ContatoDAO(this);
        mListaContatos = dao.buscaAlunos();
        dao.close();

        listaEmOrdemAlfabetica();

        adapter = new AdapterContato(this, mListaContatos);
        mRecyclerView.setAdapter(adapter);
    }

    public void listaEmOrdemAlfabetica() {
        Collections.sort(mListaContatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato o1, Contato o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarAdapter();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void RecebeContatodoAdapterENavegaParaTelaFormulario(RecebeContatoAdapter event) {
        Contato contato = event.getContato();
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        intent.putExtra("contato", contato);
        startActivity(intent);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
