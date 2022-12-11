import { Component, OnInit } from '@angular/core';
import { BookService } from '../../_services/book.service';
import { TokenStorageService } from '../../_services/token-storage.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  books:any;
  isSuccessful = false;
  isSearchFailed = false;
  errorMessage = '';
  isAuthor=false ;
  user:any;
  constructor(private bookService:BookService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.listBook();
    this.user=this.tokenStorage.getUser();
    if(this.user && this.user.roles[0] === 'ROLE_AUTHOR'){
      this.isAuthor=true;
    }else{
      this.isAuthor=false;
    }
  }

  listBook(){
    this.bookService.list().subscribe(
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


}
