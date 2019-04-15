//package com.example.ashimghimire.notepad;

//package com.example.ashimghimire.notepad.data;
package com.example.ashimghimire.notepad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashimghimire.notepad.R;
import com.example.ashimghimire.notepad.model.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    Note noteNow;
    int priorityNote;
    private onDeleteListener listener;

    public interface onDeleteListener {
        void delete(Note myNote);
    };

    public NoteAdapter(onDeleteListener listener) {
        this.listener = listener;
    }

    private List<Note> notes = new ArrayList<>();


    public void setNotes(List<Note> note) {

        this.notes = note;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        noteNow = notes.get(holder.getAdapterPosition());
        holder.txtViewTitle.setText(noteNow.getNoteTitle());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView txtViewTitle;
        private ImageView imgView;

        public NoteHolder(View itemView) {
            super(itemView);
            txtViewTitle = itemView.findViewById(R.id.titleTxt);
            imgView = itemView.findViewById(R.id.deleteView);



            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delete(noteNow);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onItemClickListner.onItemClick(noteNow);
                    }
                }
            });
        }
    }

    private OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClick(Note note);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

}
