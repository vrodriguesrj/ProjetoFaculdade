package funcaoibge.cursoandroid.com.projetofaculdade.adapter;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import funcaoibge.cursoandroid.com.projetofaculdade.R;
import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;
import funcaoibge.cursoandroid.com.projetofaculdade.eventos.RecebeContatoAdapter;
import funcaoibge.cursoandroid.com.projetofaculdade.dao.ContatoDAO;

import static android.R.attr.filter;

/**
 * Created by marcos.j.estagiario on 02/03/2017.
 */

public class AdapterContato extends RecyclerView.Adapter<AdapterContato.ContatoViewHolder> implements Filterable {

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
        if (contato.getEmail().equals("") || contato.getEmail() == null) {
            holder.email.setText("-");
        } else {
            holder.email.setText(contato.getEmail());
        }
        holder.telefone.setText(contato.getTelefone());
        String primeiraLetra = String.valueOf(contato.getNome().charAt(0));
        holder.circuloContato.setText(primeiraLetra);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                oReturn.values = mRelatorio.getListaDeResultados(constraint.toString().toLowerCase());
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mValoresParaExibicao = ((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }


    public class ContatoViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView email;
        private TextView telefone;
        private Button circuloContato;

        public ContatoViewHolder(View itemView) {
            super(itemView);

            nome = (TextView) itemView.findViewById(R.id.nome);
            email = (TextView) itemView.findViewById(R.id.email);
            telefone = (TextView) itemView.findViewById(R.id.telefone);
            circuloContato = (Button) itemView.findViewById(R.id.img_contato);

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
