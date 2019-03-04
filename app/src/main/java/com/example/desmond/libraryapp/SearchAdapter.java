package com.example.desmond.libraryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 04-Feb-18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;

    ArrayList<String> bookTitleList;
    ArrayList<String> bookNameByList;
    ArrayList<String> bookContributorList;
    ArrayList<String> bookMaterialList;
    ArrayList<String> bookPublisherList;
    ArrayList<String> bookEditionList;
    ArrayList<String> bookDescriptionList;
    ArrayList<String> bookSubjectList;
    ArrayList<String> bookCallNumberList;
    ArrayList<String> bookCopyNumberList;

    class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView b_Book_Title, c_Name_by, d_Book_Contributor, e_Book_MaterialType, f_Book_Publisher, g_Book_Edition, h_Book_Description, i_Book_Subject, j_Book_CallNumber, k_Book_CopyNumber;

        public SearchViewHolder(View itemView) {
            super(itemView);

            b_Book_Title = (TextView) itemView.findViewById(R.id.title_List);
            c_Name_by = (TextView) itemView.findViewById(R.id.name_List);
            d_Book_Contributor = (TextView) itemView.findViewById(R.id.contributor_List);
            e_Book_MaterialType = (TextView) itemView.findViewById(R.id.material_List);
            f_Book_Publisher = (TextView) itemView.findViewById(R.id.publisher_List);
            g_Book_Edition = (TextView) itemView.findViewById(R.id.edition_List);
            h_Book_Description = (TextView) itemView.findViewById(R.id.description_List);
            i_Book_Subject = (TextView) itemView.findViewById(R.id.subject_List);
            j_Book_CallNumber = (TextView) itemView.findViewById(R.id.callnumber_List);
            k_Book_CopyNumber = (TextView) itemView.findViewById(R.id.copynumber_List);

        }
    }


    public SearchAdapter(Context context, ArrayList<String> bookTitleList, ArrayList<String> bookNameByList, ArrayList<String> bookContributorList, ArrayList<String> bookMaterialList, ArrayList<String> bookPublisherList, ArrayList<String> bookEditionList,  ArrayList<String> bookDescriptionList, ArrayList<String> bookSubjectList, ArrayList<String> bookCallNumberList, ArrayList<String> bookCopyNumberList) {
        this.context = context;
        this.bookTitleList = bookTitleList;
        this.bookNameByList = bookNameByList;
        this.bookContributorList = bookContributorList;
        this.bookMaterialList = bookMaterialList;
        this.bookPublisherList = bookPublisherList;
        this.bookEditionList = bookEditionList;
        this.bookDescriptionList = bookDescriptionList;
        this.bookSubjectList = bookSubjectList;
        this.bookCallNumberList = bookCallNumberList;
        this.bookCopyNumberList = bookCopyNumberList;

    }


    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new SearchAdapter.SearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {

        holder.b_Book_Title.setText(bookTitleList.get(position));
        holder.c_Name_by.setText(bookNameByList.get(position));
        holder.d_Book_Contributor.setText(bookContributorList.get(position));
        holder.e_Book_MaterialType.setText(bookMaterialList.get(position));
        holder.f_Book_Publisher.setText(bookPublisherList.get(position));
        holder.g_Book_Edition.setText(bookEditionList.get(position));
        holder.h_Book_Description.setText(bookDescriptionList.get(position));
        holder.i_Book_Subject.setText(bookSubjectList.get(position));
        holder.j_Book_CallNumber.setText(bookCallNumberList.get(position));
        holder.k_Book_CopyNumber.setText(bookCopyNumberList.get(position));


        holder.b_Book_Title.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Title Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookTitleList.size();
    }
}
