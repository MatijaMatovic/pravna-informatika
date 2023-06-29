import "./VerdictsList.css";

import VerdictFeatures from "../../../elements/VerdictFeatures/verdictFeatures";
import { useEffect, useState } from "react";
import { getVerdictNames, getVerdictFeatures } from "../../../../services/requestService";
import { NavLink, Outlet } from "react-router-dom";

export default function VerdictsList() {
  const [verdictNames, setVerdictNames] = useState([] as Array<string>);
  const [selectedVerdict, setSelectedVerdict] = useState("");
  const [verdictFeatures, setVerdictFeatures] = useState({} as any);
  const featuresLoaded = Object.keys(verdictFeatures).length > 0;

  const selectedVerdictFeatures =
    featuresLoaded
      ? verdictFeatures[(verdictNames.indexOf(selectedVerdict)).toString()]
      : null;


  useEffect(() => {
    getVerdictNames()
      .then((data) => {
        setVerdictNames(data);
        setSelectedVerdict(data[0]);
      })
      .catch(console.error);

    getVerdictFeatures()
      .then((data) => {
        setVerdictFeatures(
          data
        );
      })
      .catch(console.error);
  }, []);

  return (
    <div className="d-flex h-100">
      <Outlet></Outlet>
      <div className="judgements-sidebar">
        <div className="title-header">Pregled presuda</div>
        <div className="sidebar-content side-width">
          <div className="my-2 overflow-auto" style={{ height: "20%" }}>
            {verdictNames &&
              verdictNames.map((name) => (
                <li key={name} className="j-nav-item">
                  <NavLink
                    onClick={() => setSelectedVerdict(name)}
                    className="j-nav-link"
                    to={`${name}`}
                  >
                    Presuda {name}
                  </NavLink>
                </li>
              ))}
          </div>
          {selectedVerdict && featuresLoaded && (
            <VerdictFeatures description={selectedVerdictFeatures as CaseDescription} />
          )}
        </div>
      </div>
    </div>
  );
}