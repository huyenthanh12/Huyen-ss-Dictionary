package customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.dictionary.R;

import java.util.ArrayList;

import model.Word;

public class pageAdapter extends PagerAdapter {

    private Context mComtext;
    private int mLayoutId;
    private ArrayList<Word> mList;

    public pageAdapter(Context context, int layout, ArrayList<Word> list){

        mComtext=context;
        mLayoutId=layout;
        mList=list;
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        LayoutInflater inflater=LayoutInflater.from(mComtext);
        View layout = inflater.inflate(mLayoutId,container,false);
        TextView tvWord = layout.findViewById(R.id.tv_name);

        Word word = mList.get(position);
        tvWord.setText(word.getName());

        container.addView(layout);
        return layout;
    }

}
