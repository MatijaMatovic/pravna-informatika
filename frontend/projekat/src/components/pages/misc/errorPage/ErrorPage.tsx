import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
  const error: any = useRouteError();
  console.error(error);

  return (
    <div className="d-flex justify-content-center align-items-center h-100">
      <div className="text-center">
        <h1>There was an error.</h1>
        <p>
          <i>{error.statusText || error.message}</i>
        </p>
      </div>
    </div>
  );
}
