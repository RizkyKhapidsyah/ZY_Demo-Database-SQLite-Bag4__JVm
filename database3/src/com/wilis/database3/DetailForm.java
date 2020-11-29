package com.wilis.database3;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DetailForm extends Activity {
	EditText nama=null;
	EditText alamat=null;
	EditText hp=null;
	RadioGroup jekel=null;
	AlmagHelper helper=null;
	String almagId=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);

		helper=new AlmagHelper(this);
		
		nama=(EditText)findViewById(R.id.nama);
		alamat=(EditText)findViewById(R.id.alamat);
		hp=(EditText)findViewById(R.id.hp);
		jekel=(RadioGroup)findViewById(R.id.jekel);
		
		Button save=(Button)findViewById(R.id.save);
		
		save.setOnClickListener(onSave);
		
		almagId=getIntent().getStringExtra(database3.ID_EXTRA);
		
		if (almagId!=null) {
			load();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	
		helper.close();
	}
	
	private void load() {
		Cursor c=helper.getById(almagId);

		c.moveToFirst();		
		nama.setText(helper.getNama(c));
		alamat.setText(helper.getAlamat(c));
		hp.setText(helper.getHp(c));
		
		if (helper.getJekel(c).equals("Pria")) {
			jekel.check(R.id.pria);
		}
		else if (helper.getJekel(c).equals("Perempuan")) {
			jekel.check(R.id.perempuan);
		}
		
		
		c.close();
	}
	
	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
			String type=null;
			
			switch (jekel.getCheckedRadioButtonId()) {
				case R.id.pria:
					type="Pria";
					break;
				case R.id.perempuan:
					type="Perempuan";
					break;
				
			}

			if (almagId==null) {
				helper.insert(nama.getText().toString(),
											alamat.getText().toString(), type,
											hp.getText().toString());
			}
			else {
				helper.update(almagId, nama.getText().toString(),
											alamat.getText().toString(), type,
											hp.getText().toString());
			}
			
			finish();
		}
	};
}