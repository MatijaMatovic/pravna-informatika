import App from "./App";
import ReasoningForm from "./components/pages/verdicts/reasoningForm/ReasoningForm";
import ErrorPage from "./components/pages/misc/errorPage/ErrorPage";
import VerdictPage, { loader as docLoader } from "./components/pages/verdicts/verdictPage/VerdictPage";
import VerdictsList from "./components/pages/verdicts/verdictsList/VerdictsList";
import LawsPage from "./components/pages/laws/lawsPage";
import NotFoundPage from "./components/pages/misc/notFoundPage/NotFoundPage";
import { RouteObject } from "react-router-dom";

const routes: RouteObject[] = [
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <LawsPage />,
      },
      {
        path: "law",
        element: <LawsPage />,
      },
      {
        path: "verdicts",
        element: <VerdictsList />,
        children: [
          {
            index: true,
            element: (
              <div className="col d-flex justify-content-center align-items-center">
                <h2>Nijedna presuda nije odabrana...</h2>
              </div>
            ),
          },
          {
            path: ":verdictID",
            element: <VerdictPage />,
            loader: docLoader as any,
          },
        ],
      },
      {
        path: "verdict-reasoning",
        element: <ReasoningForm />,
      },
      {
        path: "*",
        element: <NotFoundPage />,
      },
    ],
  },
];

export default routes;
