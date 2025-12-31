import { Injectable } from '@angular/core';
import { Product } from "../model/product";
import { Item } from "../model/item";
import { CartdetailsService } from "./cartdetails.service";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  items: Item[] = [];
  totalAmount: number = 0;
  totalSize: number = 0;

  constructor(private cartDetailsService: CartdetailsService) { }

  addToCart(product: Product) {
    const existItem = this.items.find(item => item.id === product.id);

    if (existItem) {
      existItem.quantity += 1;
    } else {
      const newItem = new Item(
        product.id,
        product.name,
        product.description,
        product.price,
        product.imagePath,
        1
      );
      this.items.push(newItem);
    }

    this.updateTotals();
  }
  updateTotals() {
    this.totalSize = this.items.reduce((sum, item) => sum + item.quantity, 0);
    this.totalAmount = this.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    this.cartDetailsService.updateCart(this.items);
    this.saveToLocalStorage();
  }

  deleteItem(itemToDelete: Item) {
    this.items = this.items.filter(item => item.id !== itemToDelete.id);

    // استخدام updateTotals لتحديث القيم وتحديث السيرفيس والـ local storage
    this.updateTotals();
  }

  saveToLocalStorage() {
    localStorage.setItem('cartItems', JSON.stringify(this.items));
  }

  loadFromLocalStorage() {
    const data = localStorage.getItem('cartItems');
    if (data) {
      this.items = JSON.parse(data);
      this.updateTotals();
    }
  }
  updateProductPrice(updatedProduct: Product) {
    this.items.forEach(item => {
      if (item.id === updatedProduct.id) {
        item.id = updatedProduct.price;
        this.totalSize= item.quantity * updatedProduct.price; // لو عندك حساب totalPrice لكل Item
      }
    });

    // إعادة حساب المجموع الكلي
    this.updateTotals();
  }
  resetCart() {
    this.items = [];
    this.updateTotals();
  }
}
