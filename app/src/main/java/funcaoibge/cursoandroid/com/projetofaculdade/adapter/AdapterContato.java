package funcaoibge.cursoandroid.com.projetofaculdade.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import funcaoibge.cursoandroid.com.projetofaculdade.R;
import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;
import funcaoibge.cursoandroid.com.projetofaculdade.dao.ContatoDAO;
import funcaoibge.cursoandroid.com.projetofaculdade.eventos.RecebeContatoAdapter;

/**
 * Created by marcos.j.estagiario on 02/03/2017.
 */

public class AdapterContato extends RecyclerView.Adapter<AdapterContato.ContatoViewHolder> {

    private Context mContext;
    private List<Contato> contatos;

    public AdapterContato(Context contexto, List<Contato> contatos) {
        this.mContext = contexto;
        this.contatos = contatos;
    }

    @Override
    public AdapterContato.ContatoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_agenda, viewGroup, false);
        return new ContatoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterContato.ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);

        holder.nome.setText(contato.getNome());
        holder.sobrenome.setText(contato.getSobrenome());

        if (contato.getEmail().equals("") || contato.getEmail() == null) {
            holder.email.setText("");
        } else {
            holder.email.setText(contato.getEmail());
        }
        holder.telefone.setText(contato.getTelefone());

        String primeiraLetra = String.valueOf(contato.getNome().charAt(0));
        holder.letraCirculo.setText(primeiraLetra);
        holder.circuloContato.setBackgroundColor(contato.getCor());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView sobrenome;
        private TextView email;
        private TextView telefone;
        private TextView letraCirculo;
        private ImageView circuloContato;

        public ContatoViewHolder(View itemView) {
            super(itemView);

            nome = (TextView) itemView.findViewById(R.id.nome);
            sobrenome = (TextView) itemView.findViewById(R.id.sobrenome);
            email = (TextView) itemView.findViewById(R.id.email);
            telefone = (TextView) itemView.findViewById(R.id.telefone);
            letraCirculo = (TextView) itemView.findViewById(R.id.texto_circulo);
            circuloContato = (ImageView) itemView.findViewById(R.id.img_contato);

             itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Selecione a opção");
                    MenuItem item = menu.add(0, v.getId(), 0, "Deletar");
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int pos = getAdapterPosition();
                            Contato contato = contatos.get(pos);
                            ContatoDAO dao = new ContatoDAO(mContext);
                            dao.remove(contato);
                            contatos.remove(contato);
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Contato contato = contatos.get(pos);
                    EventBus.getDefault().post(new RecebeContatoAdapter(contato));
                }
            });
        }
    }
}
