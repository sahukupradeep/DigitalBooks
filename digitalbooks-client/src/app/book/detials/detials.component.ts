import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../_services/book.service';

@Component({
  selector: 'app-detials',
  templateUrl: './detials.component.html',
  styleUrls: ['./detials.component.css']
})
export class DetialsComponent implements OnInit {

  book:any;
  content:any;
  id:any;
  constructor(private router: Router, private route: ActivatedRoute,private bookService:BookService) {
    let id = this.route.snapshot.paramMap.get('id');
    //console.log("-----  "+this.route.snapshot.paramMap)
    console.log("-----  "+id)
    this.loadBook(id);
    this.id=id;
   }

  ngOnInit(): void {
  }

  loadBook(id:any){
    this.bookService.getBook(id).subscribe(
      data => {
        this.book=data;
        console.log(data);
        this.content='';
        //this.isSuccessful = false;
        //this.isUpdateFailed = true;
      },
      err => {
       // this.errorMessage = err.error.message;
       // this.isUpdateFailed = true;
      })
   // this.errorMessage = '';
  }
  loadContent(subId:any){
    console.log(this.id)
    this.bookService.getBookContent(this.id).subscribe(
      data => {
        //console.log(data);
        this.content=data;
        //this.isSuccessful = false;
        //this.isUpdateFailed = true;
      },
      err => {
        //console.log(err)
       // this.errorMessage = err.error.message;
       // this.isUpdateFailed = true;
      })
  }

}
