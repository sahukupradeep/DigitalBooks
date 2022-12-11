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

  user:any;
  constructor(private http: HttpClient,private tokenStorage: TokenStorageService) { 
    this.user=tokenStorage.getUser();
  }

  search(category: string, title: string, author: number, price: number, publisher: string): Observable<any> {
    return this.http.get(API_URL + 'search?category=' + category + '&title=' + title + '&author=' + author + '&price=' + price + '&publisher=' + publisher, { responseType: 'json' });
  }
  create(logo: String, title: String, category: String, price: number, publisher: string, active: boolean, content: string) {
    this.user=this.tokenStorage.getUser();
    return this.http.post(API_URL + 'author/' + this.user.id+ '/books', { logo, title, category, price, publisher, active, content }, httpOptions);
  }
}
