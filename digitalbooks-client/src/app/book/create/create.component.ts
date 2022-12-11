import { Component, OnInit } from '@angular/core';
import { BookService } from '../../_services/book.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  form: any;
  book:any;
  books:any;
  isSuccessful = false;
  isSearchFailed = false;
  errorMessage = '';

  constructor(private bookService:BookService) { }

  ngOnInit(): void {
    this.createPage()
  }

  createPage():void{
    this.form = {
      logo:null,
      title: null,
      category:null,
      price:null,
      publisher: null,
      active:null,
      content:null
    };
    this.book={};
    this.books=[];
    this.isSuccessful = false;
    this.isSearchFailed = false;
    this.errorMessage = '';
  }


  onSubmit(): void {
    const {logo,title, category, price,publisher,active,content} = this.form;
    console.log(logo,title, category, price,publisher,active,content);
    this.bookService.create(logo,title, category, price,publisher,active,content).subscribe(
      data => {
        this.book=data;
        console.log(data);
        this.isSuccessful = true;
        this.isSearchFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSearchFailed = true;
      })
  }

}
