import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const API_URL = 'http://localhost:8080/api/v1/digitalbooks/';
@Injectable({
  providedIn: 'root'
})
export class BookService {


  user: any;
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
    this.user = tokenStorage.getUser();
  }

  search(category: string, title: string, author: number, price: number, publisher: string): Observable<any> {
    let urlParam = '';
    if (category != undefined) {
      urlParam += urlParam.length > 0 ? "&category=" + category : "?category=" + category;
    }
    if (title != undefined) {
      urlParam += urlParam.length > 0 ? "&title=" + title : "?title=" + title;
    }
    if (author != undefined) {
      urlParam += urlParam.length > 0 ? "&author=" + author : "?author=" + author;
    }
    if (price != undefined) {
      urlParam += urlParam.length > 0 ? "&price=" + price : "?price=" + price;
    }

    if (publisher != undefined) {
      urlParam += urlParam.length > 0 ? "&publisher=" + publisher : "?publisher=" + publisher;
    }
    return this.http.get(API_URL + 'search'+urlParam, { responseType: 'json' });
  }

  create(logo: String, title: String, category: String, price: number, publisher: string, active: boolean, content: string) {
    this.user = this.tokenStorage.getUser();
    return this.http.post(API_URL + 'author/' + this.user.id + '/books', { logo, title, category, price, publisher, active, content }, httpOptions);
  }

  list() {
    console.log(this.user.roles[0])
    if (this.user.roles[0] === 'ROLE_AUTHOR') {
      return this.http.get(API_URL + "author/" + this.user.id + "/books", { responseType: 'json' });
    } else {
      return this.http.get(API_URL + "readers/" + this.user.email + "/books", { responseType: 'json' });
    }
  }

  getBook(id: number) {
    if (this.user.roles[0] === 'ROLE_AUTHOR') {
      return this.http.get(API_URL + "author/" + this.user.id + "/books/" + id, { responseType: 'json' });
    } else {
      return this.http.get(API_URL + "readers/" + this.user.email + "/books/" + id, { responseType: 'json' });
    }
  }

  update(book: any) {
    return this.http.put(API_URL + 'author/' + this.user.id + '/books/' + book.id, book, httpOptions);
  }

  getBookContent(subId: any) {
    return this.http.get(API_URL + "readers/" + this.user.email + "/books/" + subId + "/read", { responseType: 'text' });
  }

  loadSearchValue() {
    return this.http.get(API_URL + "get-all/books", { responseType: 'json' });
  }
}
