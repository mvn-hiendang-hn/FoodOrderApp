package hiendtt21020315.uet.mobile.admin.list_history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import hiendtt21020315.uet.mobile.R;


public class ListHistoryFragment extends Fragment {

    private Invoce_DAO dao;
    private ArrayList<Invoice> list;
    private ArrayList<Invoice> list1;

    private Invoice_Adapter adapter;
    RecyclerView recyclerView;


    public ListHistoryFragment() {
        // Required empty public constructor
    }


    public static ListHistoryFragment newInstance() {
        ListHistoryFragment fragment = new ListHistoryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_list_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.invoice_ry);
        Invoce_DAO dao = new Invoce_DAO(getContext());
        list = dao.SeLectDaDatHang();
        Collections.reverse(list);
        adapter = new Invoice_Adapter(list, getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}