import "./App.css";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useState } from "react";
// import {
//   AlertDialog,
//   AlertDialogAction,
//   AlertDialogCancel,
//   AlertDialogContent,
//   AlertDialogDescription,
//   AlertDialogFooter,
//   AlertDialogHeader,
//   AlertDialogTitle,
// } from "@/components/ui/alert-dialog";

const FormSchema = z.object({
  username: z.string().min(1, {
    message: "Username cannot be blank.",
  }),
  password: z.string().min(1, {
    message: "Password cannot be blank.",
  }),
});

// function openAlert() {
//   return (
//     <AlertDialog>
//       <AlertDialogContent>
//         <AlertDialogHeader>
//           <AlertDialogTitle>Login successfull</AlertDialogTitle>
//           <AlertDialogDescription>
//             {/* your token is {response.token} */}
//           </AlertDialogDescription>
//         </AlertDialogHeader>
//         <AlertDialogFooter>
//           <AlertDialogCancel>Cancel</AlertDialogCancel>
//           <AlertDialogAction>Continue</AlertDialogAction>
//         </AlertDialogFooter>
//       </AlertDialogContent>
//     </AlertDialog>
//   );
// }

function App() {
  const [message, setMessage] = useState("");
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  async function onSubmit(data: z.infer<typeof FormSchema>) {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }).then((resp) => {
      return resp.json();
    });
    response.status == 500
      ? setMessage("Message - " + response.message)
      : setMessage("Your token is - " + response.token);
    // setToken(response.token);
    // return response;
    // openAlert();
  }
  return (
    <>
      <div className="w-full h-full px-96 py-10 ">
        <div className="px-52 p-40">
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(onSubmit)}
              className="border-lime-100 border-2 p-5 rounded-2xl space-y-6 "
            >
              <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Username</FormLabel>
                    <FormControl>
                      <Input placeholder="username" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Password</FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button
                type="submit"
                className="bg-gradient-to-r from-gray-400 to-blue-500 hover:from-emerald-500 hover:to-indigo-500"
              >
                Login
              </Button>
            </form>
          </Form>
          {message && (
            <div className="py-5 text-wrap break-words">{message}</div>
          )}
        </div>
      </div>
    </>
  );
}

export default App;
