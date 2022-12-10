import { Component, OnInit } from '@angular/core';
import { BookService } from 'src/app/_services/book.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form: any;
  books:any[]=[];
  isSuccessful = false;
  isSearchFailed = false;
  errorMessage = '';

  constructor(private bookService:BookService) { }

  ngOnInit(): void {
    this.back();
  }

  onSubmit(): void {
    const { category,title, author, price,publisher} = this.form;
    console.log(category,title,author,price,publisher);
    this.bookService.search(category,title,author,price,publisher).subscribe(
      data => {
        this.books=data;
        console.log(data);
        this.isSuccessful = true;
        this.isSearchFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSearchFailed = true;
      })
  }
  back():void{
    this.form = {
      category:null,
      title: null,
      author: null,
      price:null,
      publisher: null
    };
    this.books=[];
    this.isSuccessful = false;
    this.isSearchFailed = false;
    this.errorMessage = '';
  }

}
