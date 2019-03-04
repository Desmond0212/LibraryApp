package com.example.desmond.libraryapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04-Feb-18.
 */

public class BooksList extends ArrayAdapter<BooksUpdate> {

    private Activity context;
    List<BooksUpdate> books;

    public BooksList(Activity context, List<BooksUpdate> books)
    {
        super(context, R.layout.books_layout, books);
        this.context = context;
        this.books = books;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.books_layout, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.title_ListUpdate);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.description_ListUpdate);

        BooksUpdate booksUpdate = books.get(position);

        textViewTitle.setText(booksUpdate.getB_Book_Title());
        textViewName.setText(booksUpdate.getC_Name_By());

        return listViewItem;

    }
}
