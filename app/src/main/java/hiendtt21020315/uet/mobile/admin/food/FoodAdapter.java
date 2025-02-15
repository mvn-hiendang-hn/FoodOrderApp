package hiendtt21020315.uet.mobile.admin.food;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hiendtt21020315.uet.mobile.R;
import hiendtt21020315.uet.mobile.activity.ItemInforFood;
import hiendtt21020315.uet.mobile.user.history.History_DAO;
import hiendtt21020315.uet.mobile.user.home.Home;
import hiendtt21020315.uet.mobile.user.home.HomeAdapter;
import hiendtt21020315.uet.mobile.user.home.HomeDAO;

public class FoodAdapter extends  RecyclerView.Adapter<FoodAdapter.ViewHolder>  {
    Context context;
    private ArrayList<Food> list;
    private FoodDAO dao;

    public FoodAdapter(Context context, ArrayList<Food> list, FoodDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    public void setData(ArrayList<Food> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_food, null);
        return new FoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(list.get(position).getName());

        String img = list.get(position).getImg();
        Picasso.get().load(img).into(holder.iv_img);

        holder.tv_des.setText(list.get(position).getDes());
        holder.tv_price.setText(String.valueOf(list.get(position).getPrice())+" VND");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onLongClick(View v) {
                @SuppressLint("RestrictedApi") MenuBuilder builder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu_popup_edit_delete, builder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionmenu = new MenuPopupHelper(context, builder, v);
                builder.setCallback(new MenuBuilder.Callback() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getItemId() == R.id.option_edit) {
                            updateDia(list.get(position), position);
                            return true;
                        } else if (item.getItemId() == R.id.option_delete) {
                            showDele(list.get(position).getId());
                            return true;
                        } else {
                            return false;
                        }
                    }

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionmenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img;
        TextView tv_name, tv_des, tv_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_item_listFood_foodImg);
            tv_name = itemView.findViewById(R.id.tv_item_listFood_foodName);
            tv_des = itemView.findViewById(R.id.tv_item_listFood_foodContent);
            tv_price = itemView.findViewById(R.id.tv_item_listFood_foodPrice);
        }
    }
    public void showDele(int id){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_item_delete_invoice);
        TextView content = dialog.findViewById(R.id.tv_dialog_delete);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnSubmit, btnCancel;
        btnSubmit = dialog.findViewById(R.id.btn_dialog_item_delete_submit);
        btnCancel = dialog.findViewById(R.id.btn_dialog_item_delete_cancel);
        content.setText("Bạn có muốn xoá không ?");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDAO dao = new FoodDAO(context);
                if (dao.delete(id) > 0) {
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list = dao.getAllData();
                    setData(list);
                } else {
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void updateDia(Food food, int id) {
        Dialog dialog = new Dialog(context);
        FoodDAO foodDAO = new FoodDAO(context);
        TypeDAO typeDAO = new TypeDAO(context);
        dialog.setContentView(R.layout.dialog_listfood_update);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        EditText ed1, ed2;

        EditText ed_listfood_img,ed_listfood_name,ed_listfood_price,ed_listfood_des;
        Button btnDialogAddCancel, btnDialogAddSubmit;
        Spinner spn = dialog.findViewById(R.id.spn_dialog_listfood_update_type);
        ed_listfood_img = dialog.findViewById(R.id.edt_dialog_listfood_update_img);
        ed_listfood_name = dialog.findViewById(R.id.edt_dialog_listfood_update_name);
        ed_listfood_price = dialog.findViewById(R.id.edt_dialog_listfood_update_price);
        ed_listfood_des = dialog.findViewById(R.id.edt_dialog_listfood_update_des);


        ed_listfood_img.setText(list.get(id).getImg());
        ed_listfood_name.setText(list.get(id).getName());
        ed_listfood_price.setText(String.valueOf(list.get(id).getPrice()));
        ed_listfood_des.setText(list.get(id).getDes());


        btnDialogAddSubmit = dialog.findViewById(R.id.btn_dialog_listfood_update_add);
        ArrayList<TypeFood> typeList = typeDAO.getAllData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, typeDAO.name());
        spn.setAdapter(adapter1);
        int spIndex = 0;
        for (TypeFood type : typeList) {
            if (type.getTypeName().equals(food.getType())) {
                spn.setSelection(spIndex);
                break;
            }
            spIndex++;
        }
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String i = typeList.get(position).getTypeName();
                food.setType(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDialogAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDAO foodDAO1 = new FoodDAO(context);
                String img = ed_listfood_img.getText().toString();
                String name = ed_listfood_name.getText().toString();
                String priceString = ed_listfood_price.getText().toString();
                String des = ed_listfood_des.getText().toString();

                if (img.trim().isEmpty() || name.trim().isEmpty() || priceString.trim().isEmpty() || des.trim().isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!priceString.matches("\\d+")) {
                    Toast.makeText(context, "Giá tiền phải là một số", Toast.LENGTH_SHORT).show();
                } else {
                    int price = Integer.parseInt(priceString);
                    food.setImg(img);
                    food.setName(name);
                    food.setPrice(price);
                    food.setDes(des);

                    if (foodDAO1.update(food) > 0) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        list = foodDAO1.getAllData();
                        setData(list);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}
