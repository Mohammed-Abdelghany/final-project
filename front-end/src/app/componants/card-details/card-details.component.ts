import { Component, OnInit } from '@angular/core';
import { Item } from "../../../model/item";
import { CartdetailsService } from "../../../service/cartdetails.service";
import {CartService} from "../../../service/cart.service";

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent implements OnInit {
  cartItems: Item[] = [];
  totalAmount: number = 0;
  totalSize: number = 0;

  constructor(private cartDetailsService: CartdetailsService,private cartService:CartService) { }

  ngOnInit(): void {
    this.cartDetailsService.items$.subscribe(items => {
      this.cartItems = items;

      this.totalSize = items.reduce((sum, item) => sum + item.quantity, 0);
      this.totalAmount = items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    });
  }

  increaseQuantity(item: Item) {
    item.quantity++;
    this.cartDetailsService.updateCart(this.cartItems);
    this.cartService.updateTotals()
  }

  decreaseQuantity(item: Item) {
    if (item.quantity > 1) {
      item.quantity--;
      this.cartDetailsService.updateCart(this.cartItems);
      this.cartService.updateTotals()
    }
  }

  removeItem(item: Item) {
    this.cartItems = this.cartItems.filter(i => i.id !== item.id);
    this.cartService.deleteItem(item);
    this.cartDetailsService.updateCart(this.cartItems);


  }
}
