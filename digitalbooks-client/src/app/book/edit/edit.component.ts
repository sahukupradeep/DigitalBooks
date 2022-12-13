import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../_services/book.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  book:any;
  isSuccessful = false;
  isUpdateFailed = false;
  errorMessage='';
  constructor(private router: Router, private route: ActivatedRoute,private bookService:BookService) {
    let id = this.route.snapshot.paramMap.get('id');
    //console.log("-----  "+this.route.snapshot.paramMap)
    console.log("-----  "+id)
    this.loadBook(id);
   }

  ngOnInit(): void {
  }

  loadBook(id:any){
    this.bookService.getBook(id).subscribe(
      data => {
        this.book=data;
        console.log(data);
        this.isSuccessful = false;
        this.isUpdateFailed = true;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isUpdateFailed = true;
      })
    this.errorMessage = '';
  }

  
  onSubmit(): void {
    const {logo,title, category, price,publisher,active,content} = this.book;
    console.log(logo,title, category, price,publisher,active,content);
    this.bookService.update(this.book).subscribe(
      data => {
        this.book=data;
        console.log(data);
        this.isSuccessful = true;
        this.isUpdateFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isUpdateFailed = true;
      })
  }

}
