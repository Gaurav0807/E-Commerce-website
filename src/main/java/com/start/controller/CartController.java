package com.start.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.start.entities.Cart;

import com.start.entities.Product;
import com.start.repo.ProductRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.CustomerRetrieveParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.model.Card;
import com.stripe.model.Charge;




@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {
	

	@Autowired
	private ProductRepository productRepository;

	
	//creating payment
	
//	@PostMapping("/create_order")
//	@ResponseBody
//	public String createOrder(@RequestBody Map<String,Object> data)
//	{
////		System.out.println("checking function");
//		System.out.println(data);
//		return "done";
//	}
	
	
	@GetMapping("/add/{id}")
	public String add(@PathVariable int id,
			HttpSession session,Model model,
			@RequestParam(value="cartPage", required=false) String cartPage)
	{
		Product product = productRepository.getOne(id);
		if(session.getAttribute("cart")==null)
		{
			HashMap<Integer,Cart> cart= new HashMap<>();
			cart.put(id,new Cart(id,product.getName(),product.getPrice(),1,product.getImage()));
			session.setAttribute("cart", cart);
		}
		else
		{
			HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
			if(cart.containsKey(id))
			{
				int qty = cart.get(id).getQuantity();
				cart.put(id,new Cart(id,product.getName(),product.getPrice(),++qty,product.getImage()));
			}
			else
			{
				cart.put(id,new Cart(id,product.getName(),product.getPrice(),1,product.getImage()));
				session.setAttribute("cart", cart);
			}
		}
		
		HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
		int size = 0;
		double total = 0;
		
		for(Cart c : cart.values())
		{
			size+=c.getQuantity();
			total+=c.getQuantity()*Double.parseDouble(c.getPrice());
		}
		model.addAttribute("size", size);
		model.addAttribute("total", total);
		if(cartPage!=null)
		{
			return "redirect:/cart/view";
		}
		return "cartview";
	}
	
	
	@GetMapping("/subtract/{id}")
	public String subtract(@PathVariable int id,
			HttpSession session,Model model,HttpServletRequest httpservletrequest)
	{
		Product product = productRepository.getOne(id);
		HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
		int qty = cart.get(id).getQuantity();
		if(qty==1)
		{
			cart.remove(id);
			if(cart.size()==0)
			{
				session.removeAttribute("cart");
			}
		}
			else
			{
				cart.put(id,new Cart(id,product.getName(),product.getPrice(),--qty,product.getImage()));
			}
		
		String headerlink = httpservletrequest.getHeader("referer");
		
		return "redirect:"+headerlink;
		
	}
	
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable int id,
			HttpSession session,Model model,HttpServletRequest httpservletrequest)
	{
		HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
		
			cart.remove(id);
			if(cart.size()==0)
			{
				session.removeAttribute("cart");
			}
			
		String headerlink = httpservletrequest.getHeader("referer");
		
		return "redirect:"+headerlink;
		
	}
	
	@GetMapping("/clear")
	public String clear(HttpSession session,Model model,HttpServletRequest httpservletrequest)
	{
				session.removeAttribute("cart");
			
		String headerlink = httpservletrequest.getHeader("referer");
		
		return "redirect:"+headerlink;
		
	}
	
	@RequestMapping("/view")
	public String view(HttpSession session,Model m)
	{
		if(session.getAttribute("cart")==null)
		{
			return "redirect:/";
		}
		HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
		m.addAttribute("cart", cart);
		m.addAttribute("notCartViewPage",true);
		return "cart";
	}
	
	
//	@GetMapping("/checkout/{money}")
//	@ResponseBody
//	public String checkoutP(@PathVariable double money) throws StripeException
//	{
//		int i=(int)money;
//		System.out.println(i);
//		i=i*100;
//		String money1=String.valueOf(i);
//		System.out.println(money1);
//		Stripe.apiKey="sk_test_51IuYrkSBMZn0qlUjny91rgMq7vP7jtEwdWseSUJis1f644iO3llRzZOIfePtdHmJlZQeNcqJ8TQZdEeMjLmhqJGg00Z1DkH6lg";
//		 Map<String,Object> customerParameter = new HashMap<String,Object>();
//       customerParameter.put("email","nikka1@gmail.com");
//  Customer newCustomer = Customer.create(customerParameter);
////     System.out.println(newCustomer.getId());
////	//CustomerRetrieveParams r = CustomerRetrieveParams.builder().addExpand("sources").build();
//     Map<String,Object> r =  new HashMap<String,Object>();
//     List<String> expandList = new ArrayList<String>();
//     expandList.add("sources");
//     r.put("expand", expandList);
//       Customer customer = Customer.retrieve(newCustomer.getId(),r,null);//add customer id here : it will start with cus_
//		Map<String, Object> cardParam = new HashMap<String, Object>(); //add card details
//		cardParam.put("number", "4000056655665556");
//	cardParam.put("exp_month", "01");
//		cardParam.put("exp_year", "2029");
//		cardParam.put("cvc", "123");
//
//		Map<String, Object> tokenParam = new HashMap<String, Object>();
//		tokenParam.put("card", cardParam);
//
//		Token token = Token.create(tokenParam); // create a token
//
//		Map<String, Object> source = new HashMap<String, Object>();
//		source.put("source", token.getId()); //add token as source
//
//		Card card = (Card)customer.getSources().create(source); // add the customer details to which card is need to link
//		String cardDetails = card.toJson();
//		System.out.println("Card Details : " + cardDetails);
//		//customer = Customer.retrieve("cus_JPujEY8mNST4rf");		
//		//System.out.println("After adding card, customer details : " + customer);
//   		Map<String, Object> chargeParam = new HashMap<String, Object>();
//   		chargeParam.put("amount",money1);
//   		chargeParam.put("currency","INR");
//   		chargeParam.put("customer",newCustomer.getId());
//   		Charge.create(chargeParam);
//   		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//   		System.out.println(gson.toJson(newCustomer));
//   		
//
//       
//		return "ghj";
//	}
	

//	@GetMapping("/checkout")
//	public String home(Model m)
//	{
//		return "index";
//	}
//	private static final String stripePublicKey ="pk_test_51IuYrkSBMZn0qlUjVNRADmiXPViP3tPIxNCL5B2rUvVdjhk7v2Zc9nQzATfOXs90Sbyj51HNZm99F9tuLxG3GoVC00KXdF7YVq";
//
//	@RequestMapping("/checkout")
//	public String checkout(Model model) {
//		model.addAttribute("amount", 17 * 100); // in cents
//		model.addAttribute("stripePublicKey", stripePublicKey);
//		model.addAttribute("currency", ChargeRequest.Currency.USD);
//		return "checkout";
//	}
//
//	@RequestMapping(value = "/charge", method = RequestMethod.POST)
//	public String charge(ChargeRequest chargeRequest, Model model) throws StripeException {
//		chargeRequest.setDescription("Example charge");
//		chargeRequest.setCurrency(Currency.EUR);
//		Charge charge = paymentService.charge(chargeRequest);
//		model.addAttribute("id", charge.getId());
//		model.addAttribute("status", charge.getStatus());
//		model.addAttribute("chargeId", charge.getId());
//		model.addAttribute("balance_transaction", charge.getBalanceTransaction());
//		return "result";
//	}
//
//	@ExceptionHandler(StripeException.class)
//	public String handleError(Model model, StripeException ex) {
//		model.addAttribute("error", ex.getMessage());
//		return "result";
//	}
//	
//	private static final String stripePublicKey = "pk_test_51IuYrkSBMZn0qlUjVNRADmiXPViP3tPIxNCL5B2rUvVdjhk7v2Zc9nQzATfOXs90Sbyj51HNZm99F9tuLxG3GoVC00KXdF7YVq";// Put the public Key
//
//
//	@RequestMapping("/checkout/{money}")
//	public String checkout(@PathVariable double money,Model model) {
//		int i=(int)money;
//		System.out.println(i);
//		i=i*100;
//		String money1=String.valueOf(i);
//		System.out.println(money1);
//		model.addAttribute("amount", money1); // in cents
//		model.addAttribute("stripePublicKey", stripePublicKey);
//		model.addAttribute("currency", ChargeRequest.Currency.INR);
//		return "checkout";
//	}
	
	
}
















