import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Item } from '../model/item';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CartdetailsService {

  baseUrl:string = 'http://localhost:9090/checkout';

  // Observable streams for cart items and totals
  private itemsSubject = new BehaviorSubject<Item[]>([]);
  items$ = this.itemsSubject.asObservable();

  private totalAmountSubject = new BehaviorSubject<number>(0);
  totalAmount$ = this.totalAmountSubject.asObservable();

  private totalSizeSubject = new BehaviorSubject<number>(0);
  totalSize$ = this.totalSizeSubject.asObservable();

  constructor(private http: HttpClient) { }

  updateCart(items: Item[]) {
    this.itemsSubject.next(items);
    this.totalSizeSubject.next(items.reduce((sum, item) => sum + item.quantity, 0));
    this.totalAmountSubject.next(items.reduce((sum, item) => sum + (item.price * item.quantity), 0));
  }
  checkout(payload:any) {
    return this.http.post<any>(this.baseUrl,  payload);

  }



}
