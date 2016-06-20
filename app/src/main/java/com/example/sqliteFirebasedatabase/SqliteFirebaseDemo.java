package com.example.sqliteFirebasedatabase;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class SqliteFirebaseDemo extends ListActivity implements OnItemClickListener {
	JCGSQLiteHelper db = new JCGSQLiteHelper(this);
	List<Book> list;
	ArrayAdapter<String> myAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// drop this database if already exists
		db.onUpgrade(db.getWritableDatabase(), 1, 2);

		db.createBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
		db.createBook(new Book("Anna Karenina", "Leo Tolstoy"));
		db.createBook(new Book("The Grapes of Wrath", "John Steinbeck"));
		db.createBook(new Book("Invisible Man", "Ralph Ellison"));
		db.createBook(new Book("Gone with the Wind", "Margaret Mitchell"));
		db.createBook(new Book("Pride and Prejudice", "Jane Austen"));
		db.createBook(new Book("Sense and Sensibility", "Jane Austen"));
		db.createBook(new Book("Mansfield Park", "Jane Austen"));
		db.createBook(new Book("The Color Purple", "Alice Walker"));
		db.createBook(new Book("The Temple of My Familiar", "Alice Walker"));
		db.createBook(new Book("The waves", "Virginia Woolf"));
		db.createBook(new Book("Mrs Dalloway", "Virginia Woolf"));
		db.createBook(new Book("War and Peace", "Leo Tolstoy"));

		// get all books
		list = db.getAllBooks();
		List<String> listTitle = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			listTitle.add(i, list.get(i).getTitle());
		}

		myAdapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.listText, listTitle);
		getListView().setOnItemClickListener(this);
		setListAdapter(myAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// start BookActivity with extras the book id
		Intent intent = new Intent(this, BookActivity.class);
		intent.putExtra("book", list.get(arg2).getId());
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// get all books again, because something changed
		list = db.getAllBooks();

		List<String> listTitle = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			listTitle.add(i, list.get(i).getTitle());
		}

		myAdapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.listText, listTitle);
		getListView().setOnItemClickListener(this);
		setListAdapter(myAdapter);
	}
}
