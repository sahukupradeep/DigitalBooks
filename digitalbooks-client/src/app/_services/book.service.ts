import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/v1/digitalbooks/';
@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) { }

  search(category: string, title: string, author: number, price: number, publisher: string): Observable<any> {
    return this.http.get(API_URL + 'search?category=' + category + '&title=' + title + '&author=' + author + '&price=' + price + '&publisher=' + publisher, { responseType: 'json' });
  }
}
